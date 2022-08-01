/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.mwyq.enums;

import lombok.Getter;

/**
 * 敏感类别
 */
@Getter
public enum SensitiveCategory {

    NATIONALSECRITY(1,"国家安全"),
    VIOLENT(2,"暴恐"),
    LIVELIHOOD(3,"民生"),
    PORNOGRAPHIC(4,"色情"),
    CORRUPTED(5,"贪腐"),
    OTHER(6,"其他"),
    POLITICS(7,"政治");

    private Integer code;
    private String message;

    private SensitiveCategory(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getDescription(Integer code) {
        if (code == null) {
            return "";
        } else {
            for (SensitiveCategory s : SensitiveCategory.values()) {
                if (s.getCode() == code) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
