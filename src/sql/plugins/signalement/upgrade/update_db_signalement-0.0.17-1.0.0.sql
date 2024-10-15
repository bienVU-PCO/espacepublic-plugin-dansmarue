--
-- Table structure for table signalement_satisfaction_feedback
--
CREATE TABLE signalement_satisfaction_feedback (
	id_satisfaction_feedback int8 NOT NULL,
    satisfaction_feedback VARCHAR(255),
    PRIMARY KEY (id_satisfaction_feedback)
);

INSERT INTO signalement_satisfaction_feedback(id_satisfaction_feedback, satisfaction_feedback) VALUES
(1, 'Je remercie les agents pour la résolution de l’anomalie'),
(2, 'Je considère que le signalement est traité seulement en partie' ),
(3, 'Je considère que mon signalement n’a pas été traité' );

ALTER TABLE signalement_signalement
ADD fk_id_satisfaction_feedback int8;

ALTER TABLE signalement_signalement
ADD commentaire_feedback VARCHAR(255);

ALTER TABLE signalement_export
ADD commentaire_feedback VARCHAR(255);

ALTER TABLE signalement_signalement
      ADD CONSTRAINT fk_id_satisfaction_feedback_fkey FOREIGN KEY (fk_id_satisfaction_feedback) REFERENCES signalement_satisfaction_feedback(id_satisfaction_feedback);
