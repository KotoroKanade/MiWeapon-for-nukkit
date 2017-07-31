package miweapon;
import cn.nukkit.item.Item;
public interface MiWeaponAPI{
static MiWeapon getInstance(){
return MiWeapon.getInstance();
}
Item getWeapon(String name);
}
