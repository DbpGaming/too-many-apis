package dbp.tma.api.material;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dbp.tma.api.item.ItemBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.Map.Entry;

public class Part extends ItemBase {
    String partName;
    boolean enabled = true;
    int psid = 1;
    static HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
    static HashMap<String, Integer> partSets = new HashMap<String, Integer>();
    static HashMap<Integer, String> matSet = new HashMap<Integer, String>();
    static HashMap<Integer, HashMap> settingsInt = new HashMap<Integer, HashMap>();
    static HashMap<Integer, HashMap<String, String>> settingsString = new HashMap<Integer, HashMap<String, String>>();

    public Part(String modid) {
        super(modid);
    }

    public Part disable() {
        enabled = false;
        return this;
    }

    public Part enable() {
        enabled = true;
        return this;
    }

    public Part setColor(int color, int id) {
        colors.put(id, color);
        return this;
    }

    public Part setSettingsInt(HashMap settings, int id) {
        settingsInt.put(id, settings);
        return this;
    }

    public Part setSettingsString(HashMap settings, int id) {
        settingsString.put(id, settings);
        return this;
    }

    public Part setPartName(String name) {
        this.partName = name;
        this.setName(name);
        return this;
    }

    //temporarily hardcode modid until shit is fixed
    @Override
    public void registerIcons(IIconRegister register) {
        icon[0] = register.registerIcon("tma" + ":item_" + partName);
        for (Entry<String, Integer> partSet : partSets.entrySet()) {
            icon[partSet.getValue()] = register.registerIcon("tma" + ":" + partSet.getKey() + "/item_" + partName);
        }
    }

    public String getPartName() {
        return partName;
    }

    public boolean isEnabled() {
        return enabled;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemStack, int u) {
        if (colors.containsKey(itemStack.getItemDamage())) {
            return colors.get(itemStack.getItemDamage());
        }
        return 0xFFFFFF;
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        if (matSet.containsKey(meta)) {
            return icon[partSets.get(matSet.get(meta))];
        }
        return icon[0];
    }

    public Part addPartSet(String name, int matId) {
        if (name != "default") {
            if (!partSets.containsKey(name)) {
                partSets.put(name, psid);
                psid++;
            }
            matSet.put(matId, name);
        }
        return this;
    }
}
