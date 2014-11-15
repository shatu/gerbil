/**
 * The MIT License (MIT)
 *
 * Copyright (C) 2014 Agile Knowledge Engineering and Semantic Web (AKSW) (usbeck@informatik.uni-leipzig.de)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.aksw.gerbil.annotators;

import it.acubelab.batframework.problems.Sa2WSystem;

import org.aksw.gerbil.annotations.GerbilAnnotator;
import org.aksw.gerbil.config.GerbilConfiguration;
import org.aksw.gerbil.datatypes.ErrorTypes;
import org.aksw.gerbil.datatypes.ExperimentType;
import org.aksw.gerbil.exceptions.GerbilException;

@GerbilAnnotator(name = "Wikipedia Miner", couldBeCached = true, applicableForExperiments = ExperimentType.Sa2W)
public class WikipediaMinerAnnotator extends AbstractSa2WAnnotatorDecorator {

    private static final String WIKI_MINER_CONFIG_FILE_PROPERTY_NAME = "org.aksw.gerbil.annotators.WikipediaMinerAnnotatorConfig.ConfigFile";

    private String configFile;

    public WikipediaMinerAnnotator() throws GerbilException {
        configFile = GerbilConfiguration.getInstance().getString(WIKI_MINER_CONFIG_FILE_PROPERTY_NAME);
        if (configFile == null) {
            throw new GerbilException("Couldn't load config file path (\"" + WIKI_MINER_CONFIG_FILE_PROPERTY_NAME
                    + "\".", ErrorTypes.ANNOTATOR_LOADING_ERROR);
        }
    }

    @Override
    public Sa2WSystem getAnnotator() throws GerbilException {
        Sa2WSystem annotator = super.getAnnotator();
        if (annotator == null) {
            try {
                annotator = new it.acubelab.batframework.systemPlugins.WikipediaMinerAnnotator(configFile);
                setAnnotator(annotator);
            } catch (Exception e) {
                throw new GerbilException("Got an exception while loading annotator.", e,
                        ErrorTypes.ANNOTATOR_LOADING_ERROR);
            }
        }
        return annotator;
    }
}
