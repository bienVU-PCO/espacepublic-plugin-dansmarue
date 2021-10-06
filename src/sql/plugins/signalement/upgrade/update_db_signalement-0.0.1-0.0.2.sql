-- PCVB-51 Suppression des sous-menu
DELETE FROM  core_datastore WHERE LOWER(entity_key) LIKE '%piwik%' OR LOWER(entity_key) like '%piwik%';
DELETE FROM  core_datastore WHERE LOWER(entity_key) LIKE '%elastic%' OR LOWER(entity_key) like '%elastic%';
DELETE FROM core_admin_right WHERE id_right = 'PIWIK_MANAGEMENT' OR id_right = 'ELASTICDATA_MANAGEMENT' or id_right = 'STAT_ANONYMIZATION_MANAGEMENT';
DELETE FROM core_user_right WHERE id_right = 'PIWIK_MANAGEMENT' OR id_right = 'ELASTICDATA_MANAGEMENT' or id_right = 'STAT_ANONYMIZATION_MANAGEMENT';


-- DMR-2249 MAJ email usager
DROP TRIGGER IF EXISTS exp_aft_upd_signaleur ON signalement_signaleur;
DROP FUNCTION IF EXISTS exp_update_signaleur();

CREATE OR REPLACE function exp_update_signaleur()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
	update signalement_export set mail_usager = new.mail where id_signalement = new.fk_id_signalement;
    RETURN NEW;
END;
$function$

create
    trigger exp_aft_upd_signaleur after update
        on
        signalement_signaleur for each row execute procedure exp_update_signaleur()
