package me.khmdev.HUB.lang;

import java.util.*;

public class txt_es_ES extends ListResourceBundle {
	public Object[][] getContents() {
		return contenido;
	}

	private Object[][] contenido = {
			{ "ItemVisible.visible", "&AJugadores visibles" }, 
			{ "ItemVisible.invisible", "&CJugadores invisibles" },
			{ "ItemVisible.reload", "&CAun no se ha recargado el item" },
			{ "ItemVisible.name", "Visivilidad" },
			{"Base.tienda","Tienda hub"},
			{"Base.VillagerTienda","Tienda"},
			{"Base.VillagerVestidor","Vestidor"},
			{"InventoryShowKitShop.equipar","&2Equipar"},
			{"InventoryShowKitShop.volver","&CVolver"},
			{"InventoryShowKitShop.comprar","&2Comprar"},
			{"ItemKits.equipado","Equipado el kit %kit%"},
	};

}