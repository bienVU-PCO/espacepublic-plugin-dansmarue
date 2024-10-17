/*
 * Copyright (c) 2002-2024, Mairie de Paris
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

package fr.paris.lutece.plugins.dansmarue.business.dao.impl;

import fr.paris.lutece.plugins.dansmarue.business.dao.ISatisfactionFeedbackDAO;
import fr.paris.lutece.plugins.dansmarue.business.entities.SatisfactionFeedback;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

/**
 * This class provides Data Access methods for SatisfactionFeedback objects
 */
public final class SatisfactionFeedbackDAO implements ISatisfactionFeedbackDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_satisfaction_feedback, satisfaction_feedback FROM signalement_satisfaction_feedback WHERE id_satisfaction_feedback = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO signalement_satisfaction_feedback ( id_satisfaction_feedback, satisfaction_feedback ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM signalement_satisfaction_feedback WHERE id_satisfaction_feedback = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE signalement_satisfaction_feedback SET id_satisfaction_feedback = ?, satisfaction_feedback = ? WHERE id_satisfaction_feedback = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_satisfaction_feedback, satisfaction_feedback FROM signalement_satisfaction_feedback";


    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( SatisfactionFeedback satisfactionFeedback, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {

            int nIndex = 0;
            daoUtil.setInt( ++nIndex, satisfactionFeedback.getIdSatisfactionFeedback( ) );
            daoUtil.setString( ++nIndex, satisfactionFeedback.getLabel( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                satisfactionFeedback.setIdSatisfactionFeedback( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public SatisfactionFeedback load( int nId, Plugin plugin )
    {
        SatisfactionFeedback satisfactionFeedback = null;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nId );
            daoUtil.executeQuery( );



            if ( daoUtil.next( ) )
            {
                satisfactionFeedback = new SatisfactionFeedback( );

                satisfactionFeedback.setIdSatisfactionFeedback( daoUtil.getInt( "id_satisfaction_feedback" ) );
                satisfactionFeedback.setLabel( daoUtil.getString( "satisfaction_feedback" ) );
            }

        }
        return satisfactionFeedback;
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nSatisfactionFeedbackId, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nSatisfactionFeedbackId );
            daoUtil.executeUpdate( );
        }
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public void store( SatisfactionFeedback satisfactionFeedback, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, satisfactionFeedback.getIdSatisfactionFeedback( ) );
            daoUtil.setString( ++nIndex, satisfactionFeedback.getLabel( ) );
            daoUtil.setInt( ++nIndex, satisfactionFeedback.getIdSatisfactionFeedback( ) );

            daoUtil.executeUpdate( );
        }
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public List<SatisfactionFeedback> selectSatisfactionFeedbacksList( Plugin plugin )
    {
        List<SatisfactionFeedback> listSatisfactionFeedbacks = new ArrayList<>(  );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                SatisfactionFeedback satisfactionFeedback = new SatisfactionFeedback( );
                satisfactionFeedback.setIdSatisfactionFeedback( daoUtil.getInt( "id_satisfaction_feedback" ) );
                satisfactionFeedback.setLabel( daoUtil.getString( "satisfaction_feedback" ) );
                listSatisfactionFeedbacks.add( satisfactionFeedback );
            }

        }
        return listSatisfactionFeedbacks;
    }

}

