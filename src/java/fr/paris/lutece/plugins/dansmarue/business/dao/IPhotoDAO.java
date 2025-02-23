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

import fr.paris.lutece.plugins.dansmarue.business.entities.PhotoDMR;
import fr.paris.lutece.portal.service.image.ImageResource;

/**
 * The Interface IPhotoDAO.
 */
public interface IPhotoDAO
{
    /**
     * Save a new photo.
     *
     * @param photo
     *            the photo
     * @return the long
     */
    Long insert( PhotoDMR photo );

    /**
     * Delete an photo.
     *
     * @param lId
     *            the photo id
     */
    void remove( long lId );

    /**
     * Load a photo.
     *
     * @param lId
     *            the photo id
     * @return the photo
     */
    PhotoDMR load( long lId );

    /**
     * Store a photo.
     *
     * @param photo
     *            the photo object
     */
    void store( PhotoDMR photo );

    /**
     * Load the data of the image from the table.
     *
     * @param nIdPhoto
     *            The identifier of the formResponse
     * @return the image resource
     */
    ImageResource loadPhoto( int nIdPhoto );

    /**
     * Find photos for a report id.
     *
     * @param lIdSignalement
     *            the report id
     * @return list of photos
     */
    List<PhotoDMR> findBySignalementId( long lIdSignalement );

    /**
     * Find photos for a report id with photo content.
     *
     * @param lIdSignalement
     *            the report id
     * @return list of photos
     */
    List<PhotoDMR> findWithFullPhotoBySignalementId( long lIdSignalement );

    /**
     * Load a photo with the id of the report.
     *
     * @param lId
     *            the report id
     * @return the photo
     */
    PhotoDMR loadByIdSignalement( long lId );

    /**
     * Load a thumbnailed photo.
     *
     * @param nIdPhoto
     *            the photo id
     * @return the image resource
     */
    ImageResource loadPhotoThumbnail( int nIdPhoto );

    /**
     * Find photos for suppr photos daemon.
     *
     * @param anomaliesCible
     *            the anomalies cible
     * @param tempsConservation
     *            the temps conservation
     * @param etatsCible
     *            the etats cible
     * @param limitRequest
     *            the limit request
     * @return the list
     */
    List<PhotoDMR> findPhotosForSupprPhotosDaemon( List<String> anomaliesCible, Integer tempsConservation, List<String> etatsCible, Integer limitRequest );

    /**
     * Update a photo.
     *
     * @param photo
     *            the photo
     */
    void updatePhoto( PhotoDMR photo );
}
