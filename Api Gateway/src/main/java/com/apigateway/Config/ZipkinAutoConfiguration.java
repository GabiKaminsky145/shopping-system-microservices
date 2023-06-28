/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apigateway.Config;

import org.springframework.boot.actuate.autoconfigure.tracing.BraveAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.tracing.ConditionalOnEnabledTracing;
import org.springframework.boot.actuate.autoconfigure.tracing.OpenTelemetryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinConnectionDetails;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import zipkin2.Span;
import zipkin2.codec.BytesEncoder;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;


@AutoConfiguration(after = RestTemplateAutoConfiguration.class)
@ConditionalOnClass(Sender.class)
@Import({ Sender.class, Reporter.class, BraveAutoConfiguration.class,
        OpenTelemetryAutoConfiguration.class })
@ConditionalOnEnabledTracing
@EnableConfigurationProperties(ZipkinProperties.class)
public class ZipkinAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ZipkinConnectionDetails.class)
    ZipkinConnectionDetails zipkinConnectionDetails(ZipkinProperties properties) {
        return properties::getEndpoint;
    }

    @Bean
    @ConditionalOnMissingBean
    public BytesEncoder<Span> spanBytesEncoder() {
        return SpanBytesEncoder.JSON_V2;
    }

}