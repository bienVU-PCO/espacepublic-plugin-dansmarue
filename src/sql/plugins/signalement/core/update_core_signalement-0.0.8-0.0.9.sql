-- BV-80 Reliquats DMR
UPDATE core_admin_role_resource SET resource_type = 'GESTION_SUIVI_DES_MESSAGES_BIENVU' WHERE resource_type = 'GESTION_SUIVI_DES_MESSAGES_DANSMARUE';
UPDATE core_admin_role_resource SET resource_type = 'SIGNALEMENT_VILLE' WHERE resource_type = 'SIGNALEMENT_ARRONDISSEMENT';
UPDATE core_admin_role_resource SET "permission"  = 'VIEW_VILLE_SIGNALEMENT' WHERE "permission"  = 'VIEW_ARRONDISSEMENT_SIGNALEMENT';
