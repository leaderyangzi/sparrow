/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sparrow.constant.cache;

import com.sparrow.constant.magic.SYMBOL;
import com.sparrow.core.Pair;
import com.sparrow.support.ModuleSupport;
import com.sparrow.utility.StringUtility;

import java.util.Arrays;

/**
 * Created by harry on 2018/1/8.
 */
public class KEY {
    private String business;
    private Object businessId;
    private String module;

    private KEY(){}
    private KEY(Builder builder) {
        this.business = builder.business.getKey();
        this.module=builder.business.getModule();
        if (builder.businessId != null) {
            this.businessId = StringUtility.join(Arrays.asList(builder.businessId), SYMBOL.DOT);
        }
    }

    public static KEY parse(String key){
        KEY k=new KEY();
        Pair<String,String> businessWithId=Pair.split(key,SYMBOL.COLON);
        k.businessId=businessWithId.getSecond();
        String[] bussinessArray=businessWithId.getFirst().split("\\.");
        k.module=bussinessArray[0];
        k.business=businessWithId.getFirst();
        return k;
    }

    public String key() {
        if (StringUtility.isNullOrEmpty(this.businessId)) {
            return this.business;
        }
        return this.business + SYMBOL.COLON + this.businessId;
    }

    public String getBusiness() {
        return business;
    }

    public String getModule() {
        return module;
    }

    public static class Business {
        private String module;
        private String key;

        public Business(ModuleSupport module, String... business) {
            this.module = module.name();
            this.key = this.module;
            if (business != null && business.length > 0) {
                this.key += SYMBOL.DOT + StringUtility.join(Arrays.asList(business), SYMBOL.DOT);
            }
        }

        public String getKey() {
            return key;
        }

        public String getModule() {
            return module;
        }
    }

    public static class Builder {
        private Business business;
        private Object[] businessId;

        public Builder business(Business business) {
            this.business = business;
            return this;
        }

        public Builder businessId(Object... businessId) {
            this.businessId = businessId;
            return this;
        }

        public KEY build() {
            return new KEY(this);
        }
    }
}
