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
package io.github.ukuz.apollo.enhancer.internals;

import com.ctrip.framework.apollo.exceptions.ApolloConfigException;
import com.ctrip.framework.apollo.internals.Injector;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.ctrip.framework.apollo.util.ConfigUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Singleton;
import io.github.ukuz.apollo.enhancer.utils.SimpleConfigUtil;

/**
 * @author ukuz90
 */
public class SimpleInjector implements Injector {

    private com.google.inject.Injector m_injector;

    public SimpleInjector() {
        try {
            m_injector = Guice.createInjector(new SimpleModule());
        } catch (Throwable ex) {
            ApolloConfigException exception = new ApolloConfigException("Unable to initialize Guice Injector!", ex);
            Tracer.logError(exception);
            throw exception;
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        try {
            return m_injector.getInstance(clazz);
        } catch (Throwable ex) {
            Tracer.logError(ex);
            throw new ApolloConfigException(
                    String.format("Unable to load instance for %s!", clazz.getName()), ex);
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        return null;
    }

    private static class SimpleModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(ConfigUtil.class).to(SimpleConfigUtil.class).in(Singleton.class);
        }
    }
}