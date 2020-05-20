/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dansmarue.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.commons.Rights;
import fr.paris.lutece.plugins.profiles.business.Profile;
import fr.paris.lutece.plugins.profiles.business.ProfileHome;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitService;
import fr.paris.lutece.portal.business.right.Right;
import fr.paris.lutece.portal.business.right.RightHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.dashboard.DashboardComponent;
import fr.paris.lutece.portal.service.database.AppConnectionService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;


/**
 * Calendar Dashboard Component This component displays directories.
 */
public class SignalementDashboardComponent extends DashboardComponent
{

    /** The Constant MARK_URL. */
    // MARKS
    private static final String MARK_URL                      = "url";

    /** The Constant MARK_ICON. */
    private static final String MARK_ICON                     = "icon";

    /** The Constant MARK_NAME. */
    private static final String MARK_NAME                     = "name";

    /** The Constant MARK_LAST_NAME. */
    private static final String MARK_LAST_NAME                = "lastname";

    /** The Constant MARK_EMAIL. */
    private static final String MARK_EMAIL                    = "email";

    /** The Constant MARK_ENTITY. */
    private static final String MARK_ENTITY                   = "entity";

    /** The Constant MARK_PROFILE. */
    private static final String MARK_PROFILE                  = "profile";

    /** The Constant PARAMETER_PLUGIN_NAME. */
    // PARAMETERS
    private static final String PARAMETER_PLUGIN_NAME         = "plugin_name";

    /** The Constant TEMPLATE_DASHBOARD_ZONE_1. */
    // TEMPLATES
    private static final String TEMPLATE_DASHBOARD_ZONE_1     = "/admin/plugins/signalement/signalement_dashboard_zone_1.html";

    /** The Constant TEMPLATE_DASHBOARD_OTHER_ZONE. */
    private static final String TEMPLATE_DASHBOARD_OTHER_ZONE = "/admin/plugins/signalement/signalement_dashboard_other_zone.html";

    /** The Constant ZONE_1. */
    // OTHER CONSTANTS
    private static final int    ZONE_1                        = 1;

    /** The unit service. */
    // SERVICES
    private IUnitService        _unitService                  = SpringContextService.getBean( "unittree.unitService" );

    /**
     * The HTML code of the component.
     *
     * @param user            The Admin User
     * @param request            HttpServletRequest
     * @return The dashboard component
     */
    @Override
    public String getDashboardData( AdminUser user, HttpServletRequest request )
    {
        Right right = RightHome.findByPrimaryKey( getRight( ) );
        Plugin plugin = PluginService.getPlugin( right.getPluginName( ) );

        if ( !( ( plugin.getDbPoolName( ) != null ) && !AppConnectionService.NO_POOL_DEFINED.equals( plugin.getDbPoolName( ) ) ) )
        {
            return StringUtils.EMPTY;
        }

        UrlItem url = new UrlItem( right.getUrl( ) );
        url.addParameter( PARAMETER_PLUGIN_NAME, right.getPluginName( ) );

        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_URL, url.getUrl( ) );
        model.put( MARK_ICON, plugin.getIconUrl( ) );
        Rights rights = new Rights( );
        rights.init( request );
        model.put( "rights", rights );

        // Récupération des infos utilisateur
        AdminUser adminUser = AdminUserService.getAdminUser( request );
        if ( adminUser != null )
        {
            model.put( MARK_NAME, adminUser.getFirstName( ) );
            model.put( MARK_LAST_NAME, adminUser.getLastName( ) );
            model.put( MARK_EMAIL, adminUser.getEmail( ) );

            List<Profile> profils = ProfileHome.findProfileByIdUser( adminUser.getUserId( ), plugin );
            if ( !profils.isEmpty( ) )
            {
                // Un utilisateur ne peut avoir qu'un seul profil
                model.put( MARK_PROFILE, profils.get( 0 ).getDescription( ) );
            }

            List<Unit> userUnitsList = _unitService.getUnitsByIdUser( adminUser.getUserId( ), false );
            if ( !userUnitsList.isEmpty( ) )
            {
                // Un utilisateur ne peut avoir qu'une seul entité
                model.put( MARK_ENTITY, userUnitsList.get( 0 ).getDescription( ) );
            }

        }

        HtmlTemplate t = AppTemplateService.getTemplate( getTemplateDashboard( ), user.getLocale( ), model );

        return t.getHtml( );
    }

    /**
     * Get the template.
     *
     * @return the template
     */
    private String getTemplateDashboard( )
    {
        if ( getZone( ) == ZONE_1 )
        {
            return TEMPLATE_DASHBOARD_ZONE_1;
        }

        return TEMPLATE_DASHBOARD_OTHER_ZONE;
    }
}
