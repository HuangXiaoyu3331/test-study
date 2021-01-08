package com.hxy.junit.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * @author huangxy
 * @date 2021/01/08 11:52:56
 */
public class TemporaryFolderRuleTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Test
    public void test() throws IOException {
        File testFile = tmpFolder.newFile("test-file.txt");

        assertTrue("The file should have been created: ", testFile.isFile());
        assertEquals("Temp folder and test file should match: ",
                tmpFolder.getRoot(), testFile.getParentFile());
    }

}
