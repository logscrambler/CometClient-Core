package net.comet.core.module.setting;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModeSetting extends Setting<String> {
    private String value;
    private final List<String> modes;
    private int index;

    public ModeSetting(String name, String defaultValue, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.value = defaultValue;
        this.index = this.modes.indexOf(defaultValue);
    }

    public ModeSetting(String name, String defaultValue, Supplier<Boolean> visibility, String... modes) {
        super(name, visibility);
        this.modes = Arrays.asList(modes);
        this.value = defaultValue;
        this.index = this.modes.indexOf(defaultValue);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        if (modes.contains(value)) {
            this.value = value;
            this.index = modes.indexOf(value);
        }
    }
    
    public void cycle() {
        if (index < modes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
        value = modes.get(index);
    }
    
    public List<String> getModes() {
        return modes;
    }
}
