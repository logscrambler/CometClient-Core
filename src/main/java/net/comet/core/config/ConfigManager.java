package net.comet.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.comet.core.CometClient;
import net.comet.core.module.IModule;
import net.comet.core.module.Module;
import net.comet.core.module.ModuleManager;
import net.comet.core.module.setting.BooleanSetting;
import net.comet.core.module.setting.ModeSetting;
import net.comet.core.module.setting.NumberSetting;
import net.comet.core.module.setting.Setting;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 모듈의 설정을 파일로 저장하고 불러오는 관리자 클래스입니다.
 * JSON 형식을 사용하여 가독성이 좋고 수정이 쉽습니다.
 *
 * @author ReVersing
 */
public class ConfigManager {
    private static final File CONFIG_DIR = new File(FabricLoader.getInstance().getConfigDir().toFile(), "comet-core");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "modules.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save() {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        JsonObject root = new JsonObject();

        for (IModule iModule : ModuleManager.getInstance().getModules()) {
            if (iModule instanceof Module) {
                Module module = (Module) iModule;
                JsonObject moduleJson = new JsonObject();

                // 기본 상태 저장
                moduleJson.addProperty("enabled", module.isEnabled());
                moduleJson.addProperty("x", module.getX());
                moduleJson.addProperty("y", module.getY());

                // 세부 설정 저장
                JsonObject settingsJson = new JsonObject();
                for (Setting<?> setting : module.getSettings()) {
                    if (setting instanceof BooleanSetting) {
                        settingsJson.addProperty(setting.getName(), ((BooleanSetting) setting).getValue());
                    } else if (setting instanceof NumberSetting) {
                        settingsJson.addProperty(setting.getName(), ((NumberSetting) setting).getValue());
                    } else if (setting instanceof ModeSetting) {
                        settingsJson.addProperty(setting.getName(), ((ModeSetting) setting).getValue());
                    }
                }
                moduleJson.add("settings", settingsJson);

                root.add(module.getName().getString(), moduleJson);
            }
        }

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(root, writer);
            CometClient.LOGGER.info("Config saved successfully.");
        } catch (IOException e) {
            CometClient.LOGGER.error("Failed to save config", e);
        }
    }

    public static void load() {
        if (!CONFIG_FILE.exists()) return;

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (IModule iModule : ModuleManager.getInstance().getModules()) {
                if (iModule instanceof Module) {
                    Module module = (Module) iModule;
                    if (root.has(module.getName().getString())) {
                        JsonObject moduleJson = root.getAsJsonObject(module.getName().getString());

                        // 기본 상태 로드
                        if (moduleJson.has("enabled")) {
                            module.setEnabled(moduleJson.get("enabled").getAsBoolean());
                        }
                        if (moduleJson.has("x")) {
                            module.setX(moduleJson.get("x").getAsInt());
                        }
                        if (moduleJson.has("y")) {
                            module.setY(moduleJson.get("y").getAsInt());
                        }

                        // 세부 설정 로드
                        if (moduleJson.has("settings")) {
                            JsonObject settingsJson = moduleJson.getAsJsonObject("settings");
                            for (Setting<?> setting : module.getSettings()) {
                                if (settingsJson.has(setting.getName())) {
                                    JsonElement element = settingsJson.get(setting.getName());
                                    try {
                                        if (setting instanceof BooleanSetting) {
                                            ((BooleanSetting) setting).setValue(element.getAsBoolean());
                                        } else if (setting instanceof NumberSetting) {
                                            ((NumberSetting) setting).setValue(element.getAsDouble());
                                        } else if (setting instanceof ModeSetting) {
                                            ((ModeSetting) setting).setValue(element.getAsString());
                                        }
                                    } catch (Exception e) {
                                        CometClient.LOGGER.warn("Failed to load setting: " + setting.getName() + " for module " + module.getName().getString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            CometClient.LOGGER.info("Config loaded successfully.");
        } catch (IOException e) {
            CometClient.LOGGER.error("Failed to load config", e);
        }
    }
}
