package miweapon;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.Command;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Config;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.StringTag;
import cn.nukkit.nbt.tag.ByteTag;
import java.io.File;
public class MiWeapon extends PluginBase implements MiWeaponAPI{
private static MiWeapon instance=null;
public static MiWeapon getInstance(){
return instance;
}
{
instance=this;
}
@Override
public void onEnable()
{
this.getLogger().info("by mikasa qq1244456115");
this.getServer().getPluginManager().registerEvents(new EventListener(),this);
}	
public boolean getData(String name){
File file=new File(this.getDataFolder()+"/"+name+".yml");
return file.exists();
}
public void add(String name){
Config cfg=new Config(this.getDataFolder()+"/"+name+".yml",Config.YAML);
cfg.set("#ps","概率直接写数字不用％ ATK=伤害 燃烧单位为秒#");
cfg.set("id",0);
cfg.set("特殊值",0);
cfg.set("ATK",0);
cfg.set("吸血",0);
cfg.set("吸血率",0);
cfg.set("暴击",0);
cfg.set("暴击率",0);
cfg.set("雷击","开");
cfg.set("燃烧",0);
cfg.set("pve","开");
cfg.set("pvp","开");
cfg.save();
}
public void remove(String name){
File file=new File(this.getDataFolder()+"/"+name+".yml");
file.delete();
}
public void give(String player,String name,CommandSender sender){
Player p=this.getServer().getPlayer(player);
if(p!=null){
Item item=this.getWeapon(name);
p.getInventory().addItem(item);
p.sendMessage(TextFormat.GREEN+"你获得了神器"+name);
sender.sendMessage(TextFormat.GREEN+"给予成功");
}else{
sender.sendMessage(TextFormat.GREEN+"该玩家不在线");
}
}
public void rec(String player,String name,CommandSender sender){
Player p=this.getServer().getPlayer(player);
if(p!=null){
Item item=this.getWeapon(name);
if(p.getInventory().contains(item)){
p.getInventory().removeItem(item);
p.sendMessage(TextFormat.GREEN+"你失去了神器"+name);
sender.sendMessage(TextFormat.GREEN+"回收成功");
}else{
sender.sendMessage(TextFormat.GREEN+"该玩家身上没有神器");
}
}else{
sender.sendMessage(TextFormat.GREEN+"该玩家不在线");
}
}
public Item getWeapon(String name){
if(this.getData(name)){
Config cfg=new Config(this.getDataFolder()+"/"+name+".yml",Config.YAML);
int id=cfg.getInt("id");
int da=cfg.getInt("特殊值");
Item item=Item.get(id,da,1);
CompoundTag nbt=new CompoundTag();
nbt.putCompound("display",new CompoundTag("display").putString("Name",name));
nbt.putByte("Unbreakable",128);
item.setNamedTag(nbt);
return item;
}else{
this.getLogger().info(TextFormat.RED+"未找到神器数据");
}
return null;
}
@Override
public boolean onCommand(CommandSender sender, Command command, String Label, String[] args){
switch(command.getName()){
case "miweapon":
if(args.length==0){
sender.sendMessage(TextFormat.GREEN+"请输入/miweapon help查看用法");
}else{
switch(args[0]){
case "help":
sender.sendMessage(TextFormat.GREEN+"/miweapon add/del <name> 添加/删除神器");
sender.sendMessage(TextFormat.GREEN+"/miweapon give/rec player name 给予/回收玩家指定神器");
return true;
case "add":
if(args.length!=2){
sender.sendMessage(TextFormat.RED+"参数错误");
}else{
if(this.getData(args[1])){
sender.sendMessage(TextFormat.RED+"该神器已存在");
}else{
this.add(args[1]);
sender.sendMessage(TextFormat.GREEN+"添加成功");
}
}
return true;
case "del":
if(args.length!=2){
sender.sendMessage(TextFormat.RED+"参数错误");
}else{
if(!this.getData(args[1])){
sender.sendMessage(TextFormat.RED+"该神器不存在");
}else{
this.remove(args[1]);
sender.sendMessage(TextFormat.GREEN+"删除成功");
}
}
return true;
case "give":
if(args.length!=3){
sender.sendMessage(TextFormat.RED+"参数错误");
}else{
this.give(args[1],args[2],sender);
}
return true;
case "rec":
if(args.length!=3){
sender.sendMessage(TextFormat.RED+"参数错误");
}else{
this.rec(args[1],args[2],sender);
}
return true;
}
return true;
}
}
return true;
}
}