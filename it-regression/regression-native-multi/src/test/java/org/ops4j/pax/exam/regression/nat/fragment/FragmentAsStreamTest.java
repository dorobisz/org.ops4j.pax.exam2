/*
 * Copyright (C) 2011 Harald Wellmann
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
package org.ops4j.pax.exam.regression.nat.fragment;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.streamBundle;
import static org.ops4j.pax.exam.regression.nat.RegressionConfiguration.regressionDefaults;

import java.io.InputStream;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Info;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.ops4j.pax.exam.util.ServiceLookup;
import org.ops4j.pax.tinybundles.core.TinyBundle;
import org.ops4j.pax.tinybundles.core.TinyBundles;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

@RunWith( JUnit4TestRunner.class )
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class FragmentAsStreamTest
{
    @Inject
    private BundleContext bc;
    

    @Configuration( )
    public Option[] config()
    {
        return options(
            regressionDefaults(),
            mavenBundle( "org.ops4j.pax.exam", "regression-pde-bundle", Info.getPaxExamVersion() ),
            streamBundle( createFragmentBundle() ).noStart(),
            junitBundles(),
            cleanCaches() );
    }

    private InputStream createFragmentBundle()
    {
        TinyBundle bundle = TinyBundles.bundle()
            .set( Constants.FRAGMENT_HOST, "org.ops4j.pax.exam.regression.pde" )
            .set( Constants.BUNDLE_MANIFESTVERSION, "2" )
            .set( Constants.BUNDLE_SYMBOLICNAME, "org.ops4j.pax.exam.regression.fragment" )
            .add( "messages.properties", getClass().getResource( "/messages.properties" ) );

        return bundle.build();
    }

    @Test
    public void getHelloService()
    {
        for ( Bundle bundle : bc.getBundles() )
        {
            System.out.println( bundle.getSymbolicName() + " state = " + bundle.getState() );
        }
        Object service = ServiceLookup.getService( bc, "org.ops4j.pax.exam.regression.pde.HelloService" );
        assertThat( service, is( notNullValue() ) );
    }
}