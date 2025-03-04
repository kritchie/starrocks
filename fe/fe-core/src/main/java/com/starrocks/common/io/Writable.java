// Copyright 2021-present StarRocks, Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// This file is based on code available under the Apache license here:
//   https://github.com/apache/incubator-doris/blob/master/fe/fe-core/src/main/java/org/apache/doris/common/io/Writable.java

// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.starrocks.common.io;

import com.starrocks.persist.gson.GsonUtils;

import java.io.DataOutput;
import java.io.IOException;

/**
 * Any class that requires persistence should implement the Writable interface.
 * This interface requires only a uniform writable method "write()",
 * but does not require a uniform read method.
 * The usage of writable interface implementation class is as follows:
 * <p>
 * <pre> {@code
 * Class A implements Writable {

 *     @Override public void write(DataOutput out) throws IOException {
 *          out.write(x);
 *          out.write(y);
 *          ...
 *      }
 *
 *      private void readFields(DataInput in) throws IOException {
 *          x = in.read();
 *          y = in.read();
 *          ...
 *      }
 *
 *      public static A read(DataInput in) throws IOException {
 *          A a = new A();
 *          a.readFields();
 *          return a;
 *      }
 * }
 *
 * A a = new A();
 * a.write(out);
 * ...
 * A other = A.read(in);}</pre>
 * <p>
 * The "readFields()" can be implemented as whatever you like, or even without it
 * by just implementing the static read method.
 */
public interface Writable {
    /**
     * Serialize the fields of this object to <code>out</code>.
     *
     * @param out <code>DataOutput</code> to serialize this object into.
     * @throws IOException
     */
    default void write(DataOutput out) throws IOException {
        Text.writeString(out, GsonUtils.GSON.toJson(this));
    }
}
