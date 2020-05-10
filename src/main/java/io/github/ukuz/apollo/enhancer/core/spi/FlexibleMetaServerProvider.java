/*
 * Copyright 2020 ukuz90
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ukuz.apollo.enhancer.core.spi;

import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.core.spi.MetaServerProvider;
import com.ctrip.framework.apollo.core.utils.ResourceUtils;
import com.google.common.base.Strings;
import io.github.ukuz.apollo.enhancer.utils.EnvUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ukuz90
 */
public class FlexibleMetaServerProvider implements MetaServerProvider {

    public static final int ORDER = MetaServerProvider.LOWEST_PRECEDENCE + 2;
    private static final Pattern PATTERN = Pattern.compile("([a-zA-Z0-9]+)\\.meta");
    private static final Map<Env, String> domains = new HashMap();

    public FlexibleMetaServerProvider() {
        initialize();
    }

    private void initialize() {
        final Properties prop = ResourceUtils.readConfigFile("apollo-env.properties", null);

        prop.entrySet().forEach(entry -> this.processPropertyEntry(entry, prop));

    }

    private void processPropertyEntry(Map.Entry<Object, Object> entry, Properties prop) {
        if (entry.getKey() instanceof String) {
            Matcher matcher = PATTERN.matcher((CharSequence) entry.getKey());
            if (matcher.matches()) {
                String envName = matcher.group(1);
                domains.put(EnvUtils.transformEnv(envName), getMetaServerAddress(prop, envName + "_meta", envName + ".meta"));
            }
        }
    }

    private String getMetaServerAddress(Properties prop, String sourceName, String propName) {
        String metaAddress = System.getProperty(sourceName);
        if (Strings.isNullOrEmpty(metaAddress)) {
            metaAddress = System.getenv(sourceName.toUpperCase());
        }

        if (Strings.isNullOrEmpty(metaAddress)) {
            metaAddress = prop.getProperty(propName);
        }

        return metaAddress;
    }

    @Override
    public String getMetaServerAddress(Env targetEnv) {
        String metaServerAddress = domains.get(targetEnv);
        return metaServerAddress == null ? null : metaServerAddress.trim();
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
