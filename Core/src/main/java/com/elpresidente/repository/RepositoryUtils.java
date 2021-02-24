package com.elpresidente.repository;

import java.util.Map;

public interface RepositoryUtils {
    Map<String,String> loadAllScenarioName(String filePath);

    static int test(){
        return 0;
    }

}
