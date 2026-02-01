package net.comet.core.module.setting;

import java.util.function.Supplier;

public class BooleanSetting extends Setting<Boolean> {
    private boolean value;

    public BooleanSetting(String name, boolean defaultValue) {
        super(name);
        this.value = defaultValue;
    }

    public BooleanSetting(String name, boolean defaultValue, Supplier<Boolean> visibility) {
        super(name, visibility);
        this.value = defaultValue;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
    
    public void toggle() {
        this.value = !this.value;
    }
}
