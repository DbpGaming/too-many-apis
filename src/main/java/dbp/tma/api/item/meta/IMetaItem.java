package dbp.tma.api.item.meta;

import dbp.tma.api.item.instance.ItemInstance;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.List;

public interface IMetaItem {
	default String getUnlocalizedName(String modid, String metaTypeName, String itemName) {
		return "item." + modid + "." + metaTypeName + "." + itemName;
	}

	default void getSubItems(Item item, HashMap<Integer, ? extends ItemInstance> items, List itemList) {
		for (Integer id : items.keySet()) {
			itemList.add(new ItemStack(item, 1, id));
		}
	}

	default IIcon[] registerIcons(String modid, HashMap<Integer, ? extends ItemInstance> items, String metaTypeName, IIconRegister register) {
		IIcon[] icons = new IIcon[items.size()];
		for (int i = 0; i < items.size(); i++) {
			icons[i] = register.registerIcon(modid + ":item_" + metaTypeName + "_" + items.get(i).getName());
		}
		return icons;
	}

	default IIcon getIconFromDamage(IIcon[] icons, int damage) {
		IIcon icon = null;
		if (damage < icons.length) {
			icon = icons[damage];
		}
		return icon;
	}
}