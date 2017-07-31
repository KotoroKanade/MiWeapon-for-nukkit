package miweapon;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.item.Item;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.math.Vector3;
import java.io.File;
import java.util.Random;
public class EventListener implements Listener{
@EventHandler
public void onHurt(EntityDamageByEntityEvent event){
if(event.isCancelled()) return;
if(event.getDamager() instanceof Player){
Player damager;
damager=(Player)event.getDamager();
Item item=damager.getInventory().getItemInHand();
String name=item.getCustomName();
if(MiWeaponAPI.getInstance().getData(name)){
Config cfg=new Config(MiWeaponAPI.getInstance().getDataFolder()+"/"+name+".yml",Config.YAML);
if(cfg.getString("pve").equals("¿ª")){
if(event.getEntity() instanceof Player){
return;
}else{
this.pv(event,damager,name);
}
}
if(cfg.getString("pvp").equals("¿ª") && event.getEntity() instanceof Player){
this.pv(event,damager,name);
}
}
}
}
public void pv(EntityDamageByEntityEvent event,Player damager,String name){
Random ran=new Random();
int rand=ran.nextInt(101);
Config cfg=new Config(MiWeaponAPI.getInstance().getDataFolder()+"/"+name+".yml",Config.YAML);
int atk=cfg.getInt("ATK");
int xx=cfg.getInt("ÎüÑª");
int xxl=cfg.getInt("ÎüÑªÂÊ");
int bj=cfg.getInt("±©»÷");
int bjl=cfg.getInt("±©»÷ÂÊ");
int fire=cfg.getInt("È¼ÉÕ");
event.getEntity().setOnFire(fire);
if(rand<=xxl){
damager.setHealth(damager.getHealth()+xx);
event.getEntity().setHealth(event.getEntity().getHealth()-xx);
}
if(rand<=bjl){
event.setDamage(event.getDamage()+bj);
}else{
event.setDamage(event.getDamage()+atk);
}
if(cfg.getString("À×»÷").equals("¿ª")){
CompoundTag nbt=new CompoundTag();
nbt.putList(new ListTag<DoubleTag>("Pos").add(new DoubleTag("",0)).add(new DoubleTag("",0)).add(new DoubleTag("",0)));
nbt.putList(new ListTag<DoubleTag>("Motion").add(new DoubleTag("",0)).add(new DoubleTag("",0)).add(new DoubleTag("",0)));
nbt.putList(new ListTag<FloatTag>("Rotation").add(new FloatTag("",0)).add(new FloatTag("",0)));
Vector3 v3=new Vector3(event.getEntity().getX()+1.5,event.getEntity().getY()+2,event.getEntity().getZ()-1.5);
EntityLightning sd=new EntityLightning(event.getEntity().getLevel().getChunk((int)v3.getX()>>4,(int)v3.getZ()>>4),nbt);
sd.setPosition(v3);
sd.setHealth(1);
sd.spawnToAll();
}
}
}