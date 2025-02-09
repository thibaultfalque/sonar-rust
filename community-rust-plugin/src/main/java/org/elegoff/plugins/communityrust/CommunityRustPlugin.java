/**
 * Community Rust Plugin
 * Copyright (C) 2021 Eric Le Goff
 * mailto:community-rust AT pm DOT me
 * http://github.com/elegoff/sonar-rust
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.elegoff.plugins.communityrust;

import org.elegoff.plugins.communityrust.coverage.cobertura.CoberturaSensor;
import org.elegoff.plugins.communityrust.coverage.lcov.LCOVSensor;
import org.elegoff.plugins.communityrust.rules.RustRulesDefinition;
import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;
import org.elegoff.plugins.communityrust.clippy.ClippySensor;
import org.elegoff.plugins.communityrust.clippy.ClippyRulesDefinition;
import org.elegoff.plugins.communityrust.language.RustLanguage;
import org.elegoff.plugins.communityrust.language.RustQualityProfile;
import org.elegoff.plugins.communityrust.settings.RustLanguageSettings;
import org.sonar.api.resources.Qualifiers;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class CommunityRustPlugin implements Plugin {

    private static final String EXTERNAL_ANALYZERS_CATEGORY = "External Analyzers";
    private static final String RUST_SUBCATEGORY = "Rust";
    public static final String LCOV_REPORT_PATHS = "community.rust.lcov.reportPaths";
    public static final String DEFAULT_LCOV_REPORT_PATHS = "lcov.info";
    public static final String COBERTURA_REPORT_PATHS = "community.rust.cobertura.reportPaths";
    public static final String DEFAULT_COBERTURA_REPORT_PATHS = "cobertura.xml";

    @Override
    public void define(Context context) {
        context.addExtension(RustLanguage.class);
        context.addExtension(RustQualityProfile.class);

        // Add plugin settings (file extensions, etc.)
        context.addExtensions(RustLanguageSettings.getProperties());
        context.addExtensions(RustRulesDefinition.class, RustSensor.class);

        // clippy rules
        context.addExtension(ClippySensor.class);
        context.addExtensions(
                PropertyDefinition.builder(ClippySensor.REPORT_PROPERTY_KEY)
                        .name("Clippy Report Files")
                        .description("Paths (absolute or relative) to json files with Clippy issues.")
                        .category(EXTERNAL_ANALYZERS_CATEGORY)
                        .subCategory(RUST_SUBCATEGORY)
                        .onQualifiers(Qualifiers.PROJECT)
                        .multiValues(true)
                        .build(),
                ClippyRulesDefinition.class,

                LCOVSensor.class,
                PropertyDefinition.builder(LCOV_REPORT_PATHS)
                        .defaultValue(DEFAULT_LCOV_REPORT_PATHS)
                        .name("LCOV Files")
                        .description("Paths (absolute or relative) to the files with LCOV data.")
                        .onQualifiers(Qualifiers.PROJECT)
                        .subCategory("Test and Coverage")
                        .category("Rust")
                        .multiValues(true)
                        .build(),

                CoberturaSensor.class,
                PropertyDefinition.builder(COBERTURA_REPORT_PATHS)
                        .defaultValue(DEFAULT_COBERTURA_REPORT_PATHS)
                        .name("LCOV Files")
                        .description("Paths (absolute or relative) to the files with LCOV data.")
                        .onQualifiers(Qualifiers.PROJECT)
                        .subCategory("Test and Coverage")
                        .category("Rust")
                        .multiValues(true)
                        .build()


        );


    }
}
