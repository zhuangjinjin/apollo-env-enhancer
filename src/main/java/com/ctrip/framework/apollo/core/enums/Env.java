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
package com.ctrip.framework.apollo.core.enums;

import com.google.common.base.Preconditions;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Here is the brief description for all the predefined environments:
 * <ul>
 *   <li>LOCAL: Local Development environment, assume you are working at the beach with no network access</li>
 *   <li>DEV: Development environment</li>
 *   <li>FWS: Feature Web Service Test environment</li>
 *   <li>FAT: Feature Acceptance Test environment</li>
 *   <li>UAT: User Acceptance Test environment</li>
 *   <li>LPT: Load and Performance Test environment</li>
 *   <li>PRO: Production environment</li>
 *   <li>TOOLS: Tooling environment, a special area in production environment which allows
 * access to test environment, e.g. Apollo Portal should be deployed in tools environment</li>
 * </ul>
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public class Env{
    private final String name;

    public Env(String name) {
        this.name = name;
    }

    public static final Env LOCAL = new Env("LOCAL");
    public static final Env DEV = new Env("DEV");
    public static final Env FWS = new Env("FWS");
    public static final Env FAT = new Env("FAT");
    public static final Env UAT = new Env("UAT");
    public static final Env LPT = new Env("LPT");
    public static final Env PRO = new Env("PRO");
    public static final Env TOOLS = new Env("TOOLS");
    public static final Env UNKNOWN = new Env("UNKNOWN");

    public static ConcurrentHashMap<String, Env> envs = new ConcurrentHashMap<>();

    public static Env fromString(String env) {
        Env environment = EnvUtils.transformEnv(env);
        Preconditions.checkArgument(environment != UNKNOWN, String.format("Env %s is invalid", env));
        return environment;
    }

    public static Env get(String envName) {
        return envs.computeIfAbsent(envName, key -> new Env(envName));
    }

    @Override
    public String toString() {
        return name.toUpperCase();
    }
}

