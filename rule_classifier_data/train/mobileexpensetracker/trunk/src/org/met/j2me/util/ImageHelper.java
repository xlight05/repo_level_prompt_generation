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
package org.met.j2me.util;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 * 
 * To wrap image handling.
 * @author Ragupathi Palaniappan
 * @since Dec 29, 2009
 */
public class ImageHelper
{

    /**
     * Return the Image reference
     * @param imageName
     * @return The Image
     */
    public static Image getImage(String imageName)
    {
        try
        {
            return Image.createImage("/" + imageName);
        }
        catch (IOException ignore)
        {
            // This should not happen and such cases return null and let the caller handle it.
        }
        return null;

    }
}
