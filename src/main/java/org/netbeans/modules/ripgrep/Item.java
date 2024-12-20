/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.ripgrep;

import java.util.List;
import org.openide.filesystems.FileObject;

/**
 *
 * @author  Marian Petras
 */
final class Item {

    /** */
    private final ResultModel resultModel;
    /** */
    final MatchingObject matchingObj;
    /** */
    final int detailIndex;
    
    /**
     */
    Item(ResultModel resultModel, MatchingObject matchingObj, int detailIndex) {
        this.resultModel = resultModel;
        this.matchingObj = matchingObj;
        this.detailIndex = detailIndex;
    }
    
    /**
     */
    TextDetail getLocation() {
        if (detailIndex == -1) {
            return null;
        }
        FileObject fo = matchingObj.getFileObject();
        List<TextDetail> textDetails
            = matchingObj.getTextDetails();
        return detailIndex < textDetails.size()
               ? textDetails.get(detailIndex)
               : null;
    }

}
