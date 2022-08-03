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
 * 新闻类型
 */
@Getter
public enum SensitiveType {

    NEUTRAL(1,"中性新闻"),
    SENSITIVE(2,"敏感新闻"),
    FORWARD(3,"正向新闻");

    private Integer code;
    private String message;

    private SensitiveType(Integer code, String message) {
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
            for (SensitiveType s : SensitiveType.values()) {
                if (s.getCode() == code) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
