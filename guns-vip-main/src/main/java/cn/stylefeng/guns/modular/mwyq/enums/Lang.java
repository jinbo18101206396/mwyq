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
 * 语言类型
 */
@Getter
public enum Lang {

    CN("cn","中文"),
    ZANG("zang","藏文"),
    MENG("meng","蒙古文"),
    WEI("wei","维吾尔文"),
    WAIMENG("waimeng","外蒙古文");

    private String code;
    private String message;

    private Lang(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getDescription(String code) {
        if (code == null) {
            return "";
        } else {
            for (Lang s : Lang.values()) {
                if (s.getCode().equals(code)) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
