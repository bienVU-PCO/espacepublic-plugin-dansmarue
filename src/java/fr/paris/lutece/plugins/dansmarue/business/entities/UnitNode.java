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
package fr.paris.lutece.plugins.dansmarue.business.entities;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;

/**
 * The Class UnitNode.
 */
public class UnitNode
{

    /** The unit. */
    private Unit _unit;

    /** The list sub units. */
    private List<UnitNode> _listSubUnits;

    /**
     * Instantiates a new unit node.
     *
     * @param unit
     *            the unit
     */
    public UnitNode( Unit unit )
    {
        _unit = unit;
        _listSubUnits = new ArrayList<>( );
    }

    /**
     * Instantiates a new unit node.
     *
     * @param unit
     *            the unit
     * @param subUnits
     *            the sub units
     */
    public UnitNode( Unit unit, List<UnitNode> subUnits )
    {
        _unit = unit;
        _listSubUnits = subUnits;
    }

    /**
     * Gets the unit.
     *
     * @return the unit
     */
    public Unit getUnit( )
    {
        return _unit;
    }

    /**
     * Sets the unit.
     *
     * @param unit
     *            the unit to set
     */
    public void setUnit( Unit unit )
    {
        _unit = unit;
    }

    /**
     * Gets the sub units.
     *
     * @return the subUnits
     */
    public List<UnitNode> getSubUnits( )
    {
        return _listSubUnits;
    }

    /**
     * Sets the sub units.
     *
     * @param subUnits
     *            the subUnits to set
     */
    public void setSubUnits( List<UnitNode> subUnits )
    {
        _listSubUnits = subUnits;
    }
}
