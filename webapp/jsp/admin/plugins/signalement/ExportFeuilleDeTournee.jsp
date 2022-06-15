<%@ page import="fr.paris.lutece.portal.service.util.AppLogService"%>
<%@page import="fr.paris.lutece.plugins.dansmarue.business.entities.FeuilleDeTournee"%>
<%@ page errorPage="../../ErrorPage.jsp"%>
<jsp:useBean id="exportFeuilleDeTournee" scope="session"
	class="fr.paris.lutece.plugins.dansmarue.web.ManageFeuilleDeTourneeJspBean" />
<%
    response.setHeader( "Cache-Control", "must-revalidate" );
    exportFeuilleDeTournee.init( request,  "FEUILLE_DE_TOURNEE", FeuilleDeTournee.RESOURCE_TYPE, FeuilleDeTournee.PERMISSION_EXPORT );
    exportFeuilleDeTournee.doExportPDF( request, response );

%>
