/*
 * Copyright (c) 2002-2021, City of Paris
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
package fr.paris.lutece.plugins.dansmarue.service.deamon;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import fr.paris.lutece.plugins.dansmarue.business.dao.ISignalementDAO;
import fr.paris.lutece.plugins.dansmarue.business.dao.ISignaleurDAO;
import fr.paris.lutece.plugins.dansmarue.business.entities.SignalementFilter;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signaleur;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

public class AnonymisationDaemon extends Daemon
{

    private static final String EMAIL_ANONYMISE = "daemon.anonymisation.emailAno";
    
    private static final String DATASTORE_KEY = "core.daemon.AnonymisationDaemon.last.maxIdSignalement";
    
    // dao
    /** The signalement DAO. */
    private ISignalementDAO _signalementDAO = SpringContextService.getBean( "signalementDAO" );
    
    /** The signalement DAO. */
    private ISignaleurDAO _signaleurDAO = SpringContextService.getBean( "signaleurDAO" );
    
    /** The log. */
    private final Logger _log = Logger.getLogger( "Anonymisation" );
    
    @Override
    public void run( )
    {
        Calendar dateMinusNbMonth = Calendar.getInstance( );
        _log.info( "////////////////////// Lancement du daemon d'anonymisation des signaleurs au " + dateMinusNbMonth.getTime( ).toString( )
                + "//////////////////////" );
        
       SignalementFilter filters = new SignalementFilter( ); 
       List<Integer> listSignalement;
       
       int minId = Integer.parseInt( DatastoreService.getDataValue( DATASTORE_KEY, "0" ) );
       filters.setMinId( minId );
       listSignalement = _signalementDAO.getIdsSignalementByFilter( filters, null );
       
       _log.info( "Anonymisation des informations des signaleurs des anomalies suivantes : " + listSignalement.toString( ) );
       
       for( int idSignalement : listSignalement ) {
           try {
               Signaleur signaleur = _signaleurDAO.loadByIdSignalement( idSignalement );
               signaleur.setMail( EMAIL_ANONYMISE );
               signaleur.setIdTelephone( null );
               _signaleurDAO.update( signaleur );
           } catch (Exception e) {
               _log.error( "Une erreur est survenu lors de l'anonymisation du signaleur de l'anomalie " + idSignalement + " :\n" + e.getCause( ));
           }
           
           minId = idSignalement;
       }
       
       DatastoreService.setDataValue( DATASTORE_KEY, String.valueOf( minId ) );
       
       _log.info( listSignalement.size( ) + " signaleurs ont été anonymisés" );
       
    }
}
