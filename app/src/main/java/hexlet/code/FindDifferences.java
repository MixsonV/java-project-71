package hexlet.code;

import java.util.Map;
import java.util.Set;
import java.util.Objects;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.TreeSet;


public final class FindDifferences {

    public static Map<String, Map<String, Object>> getDiff(Map<String, Object> data1, Map<String, Object> data2) {
        // Получаем все ключи из обоих данных, без повторений
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(data1.keySet());
        allKeys.addAll(data2.keySet());

        Map<String, Map<String, Object>> result = new TreeMap<>();

        for (String key : allKeys) {
            Map<String, Object> diffDetails = new HashMap<>();
            // Получаем значения для ключа из обоих data
            Object value1 = data1.get(key);
            Object value2 = data2.get(key);
            // Сравниваем ключи и значения
            if (!data2.containsKey(key)) {
                diffDetails.put("value", value1);
                diffDetails.put("status", "removed");
            } else if (!data1.containsKey(key)) {
                diffDetails.put("value", value2);
                diffDetails.put("status", "added");
            } else if (!Objects.equals(value1, value2)) {
                diffDetails.put("oldValue", value1);
                diffDetails.put("newValue", value2);
                diffDetails.put("status", "updated");
            } else {
                diffDetails.put("value", value1);
                diffDetails.put("status", "unchanged");
            }
            result.put(key, diffDetails);
        }
        return result;
    }

}
