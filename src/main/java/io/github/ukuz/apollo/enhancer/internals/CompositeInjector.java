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
import com.ctrip.framework.apollo.internals.DefaultInjector;
import com.ctrip.framework.apollo.internals.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ukuz90
 */
public class CompositeInjector implements Injector {

    private final List<Injector> injectors;

    public CompositeInjector() {
        injectors = new ArrayList<>();
        injectors.add(new SimpleInjector());
        injectors.add(new DefaultInjector());
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        Throwable ex = null;
        for (Injector injector : injectors) {
            try {
                return injector.getInstance(clazz);
            } catch (ApolloConfigException e) {
                ex = e;
            }
        }
        throw new ApolloConfigException(
                String.format("Unable to load instance for %s!", clazz.getName()), ex);
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        return null;
    }
}
