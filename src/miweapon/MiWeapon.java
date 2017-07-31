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
cfg.set("#ps","����ֱ��д���ֲ��ã� ATK=�˺� ȼ�յ�λΪ��#");
cfg.set("id",0);
cfg.set("����ֵ",0);
cfg.set("ATK",0);
cfg.set("��Ѫ",0);
cfg.set("��Ѫ��",0);
cfg.set("����",0);
cfg.set("������",0);
cfg.set("�׻�","��");
cfg.set("ȼ��",0);
cfg.set("pve","��");
cfg.set("pvp","��");
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
p.sendMessage(TextFormat.GREEN+"����������"+name);
sender.sendMessage(TextFormat.GREEN+"����ɹ�");
}else{
sender.sendMessage(TextFormat.GREEN+"����Ҳ�����");
}
}
public void rec(String player,String name,CommandSender sender){
Player p=this.getServer().getPlayer(player);
if(p!=null){
Item item=this.getWeapon(name);
if(p.getInventory().contains(item)){
p.getInventory().removeItem(item);
p.sendMessage(TextFormat.GREEN+"��ʧȥ������"+name);
sender.sendMessage(TextFormat.GREEN+"���ճɹ�");
}else{
sender.sendMessage(TextFormat.GREEN+"���������û������");
}
}else{
sender.sendMessage(TextFormat.GREEN+"����Ҳ�����");
}
}
public Item getWeapon(String name){
if(this.getData(name)){
Config cfg=new Config(this.getDataFolder()+"/"+name+".yml",Config.YAML);
int id=cfg.getInt("id");
int da=cfg.getInt("����ֵ");
Item item=Item.get(id,da,1);
CompoundTag nbt=new CompoundTag();
nbt.putCompound("display",new CompoundTag("display").putString("Name",name));
nbt.putByte("Unbreakable",128);
item.setNamedTag(nbt);
return item;
}else{
this.getLogger().info(TextFormat.RED+"δ�ҵ���������");
}
return null;
}
@Override
public boolean onCommand(CommandSender sender, Command command, String Label, String[] args){
switch(command.getName()){
case "miweapon":
if(args.length==0){
sender.sendMessage(TextFormat.GREEN+"������/miweapon help�鿴�÷�");
}else{
switch(args[0]){
case "help":
sender.sendMessage(TextFormat.GREEN+"/miweapon add/del <name> ���/ɾ������");
sender.sendMessage(TextFormat.GREEN+"/miweapon give/rec player name ����/�������ָ������");
return true;
case "add":
if(args.length!=2){
sender.sendMessage(TextFormat.RED+"��������");
}else{
if(this.getData(args[1])){
sender.sendMessage(TextFormat.RED+"�������Ѵ���");
}else{
this.add(args[1]);
sender.sendMessage(TextFormat.GREEN+"��ӳɹ�");
}
}
return true;
case "del":
if(args.length!=2){
sender.sendMessage(TextFormat.RED+"��������");
}else{
if(!this.getData(args[1])){
sender.sendMessage(TextFormat.RED+"������������");
}else{
this.remove(args[1]);
sender.sendMessage(TextFormat.GREEN+"ɾ���ɹ�");
}
}
return true;
case "give":
if(args.length!=3){
sender.sendMessage(TextFormat.RED+"��������");
}else{
this.give(args[1],args[2],sender);
}
return true;
case "rec":
if(args.length!=3){
sender.sendMessage(TextFormat.RED+"��������");
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