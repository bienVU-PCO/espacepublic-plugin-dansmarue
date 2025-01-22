--
-- Table structure for table signalement_satisfaction_feedback
--
CREATE TABLE signalement_satisfaction_feedback (
	id_satisfaction_feedback int8 NOT NULL,
    satisfaction_feedback VARCHAR(255),
    PRIMARY KEY (id_satisfaction_feedback)
);

GRANT ALL PRIVILEGES ON TABLE signalement_satisfaction_feedback TO bienvu;

INSERT INTO signalement_satisfaction_feedback(id_satisfaction_feedback, satisfaction_feedback) VALUES
(1, 'Je remercie les agents pour la résolution de l’anomalie'),
(2, 'Je considère que le signalement est traité seulement en partie' ),
(3, 'Je considère que mon signalement n’a pas été traité' );

ALTER TABLE signalement_signalement
ADD fk_id_satisfaction_feedback int8;

ALTER TABLE signalement_signalement
ADD commentaire_feedback VARCHAR(255);

ALTER TABLE signalement_signalement
ADD nombre_feedback SMALLINT;

ALTER TABLE signalement_export
ADD commentaire_feedback VARCHAR(255);

ALTER TABLE signalement_export
ADD nature_feedback VARCHAR(255);

ALTER TABLE signalement_export
ADD nombre_feedback SMALLINT;

ALTER TABLE signalement_signalement
      ADD CONSTRAINT fk_id_satisfaction_feedback_fkey FOREIGN KEY (fk_id_satisfaction_feedback) REFERENCES signalement_satisfaction_feedback(id_satisfaction_feedback);


CREATE OR REPLACE FUNCTION test_exp_ins_sign()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
 Newnumero varchar(15) := concat(new.prefix,new.annee,new.mois,new.numero);
 Newpriorite varchar(50);
 TypeAno text;
 TypeAnoTemp varchar(255);
 Fk_type_parent integer;
 NewTypeSignalementAlias varchar(255);
 NewTypeSignalementAliasMobile varchar(255);
 NewLabelDirection varchar(255);
 NewIdDirection integer;
 NewArrondissement varchar (255);
 NewSecteurAffectation varchar (255);
 NewMailUsager varchar (255);
 NewNombrePhotos integer ;
 NewRaisonRejet text;
 NewPhotoServiceFait integer;
 NewSatisfactionFeedback varchar (255);
begin

		 -- Priorité
		 select libelle into Newpriorite from signalement_priorite where id_priorite = new.fk_id_priorite;

         -- Satisfaction Feedback
		 select satisfaction_feedback into NewSatisfactionFeedback from signalement_satisfaction_feedback where id_satisfaction_feedback = new.fk_id_satisfaction_feedback;

		 -- Alias et Alias Mobile
		 select alias, alias_mobile into NewTypeSignalementAlias, NewTypeSignalementAliasMobile from signalement_type_signalement_alias where fk_id_type_signalement =  new.fk_id_type_signalement;

		 -- Recherche du type d'anomalie (concaténation jusqu'au dernier parent)
		 select libelle, fk_id_type_signalement into TypeAno, Fk_type_parent from signalement_type_signalement where id_type_signalement = new.fk_id_type_signalement;
		 WHILE Fk_type_parent IS NOT NULL LOOP
			select libelle into TypeAnoTemp from signalement_type_signalement where id_type_signalement = Fk_type_parent;
			TypeAno := concat(TypeAnoTemp, ' > ',  TypeAno);

			select fk_id_type_signalement into Fk_type_parent from signalement_type_signalement where id_type_signalement = Fk_type_parent;

		 END LOOP;

		 -- Direction et secteur d'affectation
		 select uus.id_unit, unit.label, sector.name into NewIdDirection, NewLabelDirection, NewSecteurAffectation
			from unittree_unit_sector as uus
			inner join unittree_unit as unit on unit.id_unit = uus.id_unit
			inner join unittree_sector as sector on uus.id_sector=sector.id_sector
			where uus.id_sector = new.fk_id_sector and unit.id_parent = 0;

		 -- Arrondissement
		 select numero_arrondissement into NewArrondissement from signalement_arrondissement where id_arrondissement = new.fk_id_arrondissement;

		 -- Mail usager
		 select mail into NewMailUsager from signalement_signaleur where fk_id_signalement = new.id_signalement;

		 -- Photos
		 select count (id_photo) into NewNombrePhotos from signalement_photo where date_photo is not null and fk_id_signalement = new.id_signalement;

		 -- Raison de rejet
		select array_to_string(array(select libelle from signalement_observation_rejet as sor
		left join signalement_observation_rejet_signalement as sors on sors.fk_id_observation_rejet = sor.id_observation_rejet
		where sors.fk_id_signalement=new.id_signalement) ||
		(select array_agg(observation_rejet_comment) from signalement_observation_rejet_signalement
		where fk_id_signalement=new.id_signalement and observation_rejet_comment is not null),', ') into NewRaisonRejet ;

		 -- Photo service fait
		 select (exists (select 1 from signalement_photo where fk_id_signalement=new.id_signalement and vue_photo=2))::int into NewPhotoServiceFait;



		if exists (select 1 from signalement_export where id_signalement=new.id_signalement)
			then
				update signalement_export set id_type_signalement=new.fk_id_type_signalement, id_direction=NewIdDirection, id_sector=new.fk_id_sector, id_arrondissement = new.fk_id_arrondissement, numero =Newnumero, priorite=Newpriorite, type_signalement=TypeAno, alias=NewTypeSignalementAlias, alias_mobile=NewTypeSignalementAliasMobile, direction=NewLabelDirection, arrondissement=NewArrondissement, secteur=NewSecteurAffectation, date_creation=to_char(new.date_creation,'DD/MM/YYYY'), heure_creation=to_char(new.date_creation,'HH24:MI'),mail_usager=NewMailUsager, commentaire_usager=new.commentaire, nb_photos=NewNombrePhotos, raisons_rejet=NewRaisonRejet, nb_suivis=new.suivi, nb_felicitations=new.felicitations, is_photo_service_fait=NewPhotoServiceFait, mail_destinataire_courriel=new.courriel_destinataire, courriel_expediteur=new.courriel_expediteur, date_envoi_courriel=to_char(new.courriel_date,'DD/MM/YYYY'), date_prevu_traitement = to_char(new.date_prevue_traitement,'DD/MM/YYYY'),commentaire_agent_terrain=new.commentaire_agent_terrain, commentaire_feedback=new.commentaire_feedback, nature_feedback=NewSatisfactionFeedback, nombre_feedback=new.nombre_feedback
				where id_signalement=new.id_signalement;
			else
				insert into signalement_export(id_signalement, id_type_signalement, id_direction, id_sector, id_arrondissement, numero, priorite, type_signalement, alias, alias_mobile, direction, arrondissement, secteur, date_creation, heure_creation, mail_usager, commentaire_usager, nb_photos, raisons_rejet, nb_suivis, nb_felicitations, is_photo_service_fait, mail_destinataire_courriel, courriel_expediteur, date_envoi_courriel, date_prevu_traitement, commentaire_agent_terrain, commentaire_feedback, nature_feedback, nombre_feedback )
				values(new.id_signalement,new.fk_id_type_signalement, NewIdDirection, new.fk_id_sector, new.fk_id_arrondissement, Newnumero, Newpriorite, TypeAno, NewTypeSignalementAlias, NewTypeSignalementAliasMobile, NewLabelDirection, NewArrondissement, NewSecteurAffectation, to_char(new.date_creation,'DD/MM/YYYY'), to_char(new.date_creation,'HH24:MI'),NewMailUsager, new.commentaire, NewNombrePhotos, NewRaisonRejet, new.suivi, new.felicitations, NewPhotoServiceFait, new.courriel_destinataire, new.courriel_expediteur, to_char(new.courriel_date,'DD/MM/YYYY'), to_char(new.date_prevue_traitement,'DD/MM/YYYY'), new.commentaire_agent_terrain, new.commentaire_feedback, NewSatisfactionFeedback, new.nombre_feedback );
		end if;




    RETURN NEW;
END;
$function$
;

ALTER TABLE signalement_export ADD date_requalification varchar(10) DEFAULT '' NULL;
ALTER TABLE signalement_export ADD heure_requalification varchar(5) DEFAULT '' NULL;
ALTER TABLE signalement_export ADD date_programme varchar(10) DEFAULT '' NULL;
ALTER TABLE signalement_export ADD heure_programme varchar(5) DEFAULT '' NULL;

CREATE OR REPLACE FUNCTION test_exp_ins_wkf()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
declare
 NewEtat varchar(255);
 NewIdMailServiceFait integer;
 NewDateCloture varchar (10) ;
 UserAccessCode varchar (255);
 IdLastHistory integer;
 NewExecuteurServiceFait varchar (255) :='';
 UserAdminEmail varchar (255);
 NewDerniereDateMAJ varchar (10);
 NewNombrePhotos integer;
 NewDateRequalification varchar(10) := '';
 NewHeureRequalification varchar(5) := '';
 NewDateEtatProgramme varchar(10) := '';
 NewHeureEtatProgramme varchar(5) := '';
begin

	-- état
	SELECT ws.name into NewEtat from workflow_state as ws where ws.id_state=new.id_state;

	-- date de cloture
	if new.id_state=10
		THEN select to_char(service_fait_date_passage, 'DD/MM/YYYY') into NewDateCloture from signalement_signalement where new.id_resource=id_signalement ;
	elseif new.id_state=22
		THEN select to_char(date_mise_surveillance, 'DD/MM/YYYY') into NewDateCloture  from signalement_signalement where new.id_resource=id_signalement;
	elseif new.id_state=11
		THEN select to_char(date_rejet, 'DD/MM/YYYY') into NewDateCloture  from signalement_signalement where new.id_resource=id_signalement;
	else NewDateCloture  :='';
	end if;

	-- date de requalification
	if new.id_state=18
		THEN
			select to_char(NOW(), 'DD/MM/YYYY'), to_char(NOW(), 'hh24:mi') into NewDateRequalification, NewHeureRequalification;
    else
        select date_requalification, heure_requalification into NewDateRequalification, NewHeureRequalification from signalement_export WHERE id_signalement=new.id_resource;
	end if;

	-- date etat programmé
	if new.id_state=9
		THEN
			select to_char(NOW(), 'DD/MM/YYYY'), to_char(NOW(), 'hh24:mi') into NewDateEtatProgramme, NewHeureEtatProgramme;
    else
        select date_programme, heure_programme into NewDateEtatProgramme, NewHeureEtatProgramme from signalement_export WHERE id_signalement=new.id_resource;

	end if;


	-- id mail service fait
	select val.id_message into NewIdMailServiceFait
	from signalement_workflow_notifuser_multi_contents_value as val
	inner join workflow_resource_history as history on history.id_history = val.id_history
	where history.id_resource=new.id_resource order by history.id_history desc limit 1;

	-- date de dernière prise en compte
	select id_history, to_char(creation_date, 'DD/MM/YYYY') into IdLastHistory, NewDerniereDateMAJ from workflow_resource_history where id_resource = new.id_resource order by creation_date desc limit 1;


	-- Exécuteur service fait (code user)
	select user_access_code into UserAccessCode from workflow_resource_history where id_resource = new.id_resource and id_action in (62,70,22,18,49,53,41);

	-- Mail exécuteur service fait
	select email into UserAdminEmail from  core_admin_user where access_code=UserAccessCode;

	-- Nom exécuteur service fait
	if UserAccessCode ='auto'
		then if (((select length(id_telephone) from  signalement_signaleur where fk_id_signalement = new.id_resource) > 0) and ( (select length(mail) from  signalement_signaleur where fk_id_signalement = new.id_resource) = 0 ))
				then NewExecuteurServiceFait := 'Mobil user';
			 else select mail into  NewExecuteurServiceFait from  signalement_signaleur where fk_id_signalement = new.id_resource;
			 end if;
	elseif length(UserAdminEmail)>0
		then NewExecuteurServiceFait := UserAdminEmail;

	end if;

     -- Photos
	select count (id_photo) into NewNombrePhotos from signalement_photo where fk_id_signalement = new.id_resource;

	if exists (select 1 from signalement_export where id_signalement=new.id_resource)
		then
			UPDATE signalement_export
			set id_wkf_state= new.id_state,etat=NewEtat, id_mail_service_fait=NewIdMailServiceFait, date_cloture=NewDateCloture, executeur_service_fait=NewExecuteurServiceFait, date_derniere_action=NewDerniereDateMAJ, nb_photos=NewNombrePhotos,
				date_requalification=NewDateRequalification, heure_requalification=NewHeureRequalification, date_programme=NewDateEtatProgramme, heure_programme=NewHeureEtatProgramme
			where id_signalement = new.id_resource;
	end if;

    RETURN NEW;
END;
$function$
;
