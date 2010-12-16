/* Licensed under the Apache License, Version 2.0 (the "License");
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

package org.activiti.engine.test.bpmn.deployment;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.test.Deployment;


/**
 * @author Joram Barrez
 */
public class DiagramGenerationTest extends PluggableActivitiTestCase {
  
  @Deployment
  public void testGeneratedDiagramMatchesExpected() throws IOException {
    String imageLocation = "org/activiti/engine/test/bpmn/deployment/DiagramGenerationTest.testGeneratedDiagramMatchesExpected.png";
    BufferedImage expectedImage = ImageIO.read(ReflectUtil.getResourceAsStream(imageLocation));

    // Need to to this crap, because the expected image was created on a mac,
    // and QA runs on Windows and apparantly pixels are stored differently on each os ... 
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice device = environment.getDefaultScreenDevice();
    GraphicsConfiguration config = device.getDefaultConfiguration();
    BufferedImage compatibleImage = config.createCompatibleImage(expectedImage.getWidth(), expectedImage.getHeight(), Transparency.TRANSLUCENT);
    compatibleImage.createGraphics().drawImage(expectedImage, 0, 0, null);
    compatibleImage.flush();
    
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
    BufferedImage imageInRepo = ImageIO.read(repositoryService.getResourceAsStream(
            processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName()));
    assertNotNull(imageInRepo);
    
    // Pixel wise comparison
    for (int x = 0; x < compatibleImage.getWidth(); x++) {
      for (int y = 0; y < compatibleImage.getHeight(); y++) {
        assertEquals(compatibleImage.getRGB(x, y), imageInRepo.getRGB(x, y));
      }
    }
  }

}
