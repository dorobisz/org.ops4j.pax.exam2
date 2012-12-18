/*
 * Copyright 2012 Harald Wellmann
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
package org.ops4j.pax.exam.regression.multi.inject;

import static org.junit.Assert.fail;

import java.sql.Driver;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

/**
 * Service lookup for injecting a non-existing service should time out.
 * 
 * @author Harald Wellmann
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ServiceTimeout {

    @Inject
    private Driver missingService;

    @Test
    public void getInjectedService() {
        missingService.getMajorVersion();
        fail("this method should never run");
    }
}
