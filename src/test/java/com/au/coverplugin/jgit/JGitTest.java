package com.au.coverplugin.jgit;

import com.au.coverplugin.domain.GitRepository;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 4:55 PM 2019/2/14
 */
public class JGitTest {

    @Test
    public void testBranch(){
        File file = new File("/Users/mengqingshui/eclipse-workspace/coverplugin");
        try {
            new GitRepository(file).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
