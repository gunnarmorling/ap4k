/**
 * Copyright 2018 The original authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
**/


package io.ap4k.config;


import io.ap4k.deps.kubernetes.api.builder.VisitableBuilder;
import io.ap4k.kubernetes.config.Configurator;

import java.util.function.Supplier;

/**
 * This is a Facade around configuration builders, which hide the builder specifics and only allows the use of {@link Configurator} as visitors.
 * @param <C> The configuration class.
 */
public class ConfigurationSupplier<C> implements Supplier<C> {

  private final VisitableBuilder<C, ?> builder;

  public ConfigurationSupplier (VisitableBuilder<C, ?> builder) {
    this.builder = builder; 
  }

  public C get() {
    return builder.build();
  }

  public ConfigurationSupplier<C> configure(Iterable<Configurator> configurators) {
    configurators.forEach(v -> builder.accept(v));
    return this;
  }

  public ConfigurationSupplier<C> configure(Configurator configurator) {
    builder.accept(configurator);
    return this;
  }
}
