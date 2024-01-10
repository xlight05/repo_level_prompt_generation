/*
 * Copyright 2009 Ragupathi Palaniappan Licensed under the Apache License, Version 2.0 (the
 * "License");
 * 
 * You may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.met.j2me;

/**
 * 
 * @author Ragupathi Palaniappan
 * @since Jan 10, 2010
 */
public class RGBPresenter
{
    private int[] backGround;
    private int[] foreGround;
    private int[] selection;

    /**
     * Returns the backGround.
     * 
     * @return the backGround
     */
    public int[] getBackGround()
    {
        return backGround;
    }

    /**
     * Sets the backGround.
     * 
     * @param backGround the backGround to set
     */
    public void setBackGround(int[] backGround)
    {
        this.backGround = backGround;
    }

    /**
     * Returns the foreGround.
     * 
     * @return the foreGround
     */
    public int[] getForeGround()
    {
        return foreGround;
    }

    /**
     * Sets the foreGround.
     * 
     * @param foreGround the foreGround to set
     */
    public void setForeGround(int[] foreGround)
    {
        this.foreGround = foreGround;
    }

    /**
     * Returns the selection.
     * 
     * @return the selection
     */
    public int[] getSelection()
    {
        return selection;
    }

    /**
     * Sets the selection.
     * 
     * @param selection the selection to set
     */
    public void setSelection(int[] selection)
    {
        this.selection = selection;
    }

}
