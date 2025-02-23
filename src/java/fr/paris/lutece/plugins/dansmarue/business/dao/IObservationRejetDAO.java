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
package fr.paris.lutece.plugins.dansmarue.business.dao;

import java.util.List;

import fr.paris.lutece.plugins.dansmarue.business.entities.ObservationRejet;
import fr.paris.lutece.portal.service.plugin.Plugin;

/**
 * The Interface IObservationRejetDAO.
 */
public interface IObservationRejetDAO
{

    /**
     * Save a new reject observation.
     *
     * @param observationRejet
     *            the reject observation
     * @return observationRejet id
     */
    public Integer insert( ObservationRejet observationRejet );

    /**
     * Delete a reject observation.
     *
     * @param lId
     *            the observationRejet id
     */
    public void remove( long lId );

    /**
     * Load a reject observation.
     *
     * @param lId
     *            the observationRejet id
     * @return a reject observation
     */
    public ObservationRejet load( Integer lId );

    /**
     * Store a reject observation.
     *
     * @param observationRejet
     *            the observationRejet object
     */
    public void store( ObservationRejet observationRejet );

    /**
     * Return all reject observation.
     *
     * @param plugin
     *            the plugin
     * @return a list of ObservationRejet
     */
    public List<ObservationRejet> getAllObservationRejet( Plugin plugin );

    /**
     * Check if the observationRejet already exists.
     *
     * @param observationRejet
     *            the observationRejet object
     * @return boolean
     */
    public boolean existsObservationRejet( ObservationRejet observationRejet );

    /**
     * Return all active ObservationRejet.
     *
     * @return a list of active reject observation
     */
    List<ObservationRejet> getAllObservationRejetActif( );

    /**
     * Updates a reject observation order, by its id.
     *
     * @param observationRejet
     *            the observationRejet object
     */
    void updateObservationRejetOrdre( ObservationRejet observationRejet );

    /**
     * Decreases the order of the next reject.
     *
     * @param observationRejet
     *            the observationRejet object
     */
    void decreaseOrdreOfNextRejet( ObservationRejet observationRejet );

    /**
     * Increases the order of the previous reject.
     *
     * @param observationRejet
     *            the observationRejet object
     */
    void increaseOrdreOfPreviousRejet( ObservationRejet observationRejet );

    /**
     * Increases all the next orders.
     *
     * @param nIdObservationRejet
     *            the reject observation id
     */
    void increaseOrdreOfAllNext( int nIdObservationRejet );

    /**
     * Decreases all the next orders.
     *
     * @param nIdObservationRejet
     *            the reject observation id
     */
    void decreaseOrdreOfAllNext( int nIdObservationRejet );

    /**
     * Returns the number of reject observation existing.
     *
     * @return the number of reject observation
     */
    int getObservationRejetCount( );

    /**
     * Counts the number of time the reject observation has been used.
     *
     * @param idObservationRejet
     *            the id of reject observation to get the use count
     * @return the number of reject observation uses
     */
    int countByIdObservationRejet( int idObservationRejet );

}
