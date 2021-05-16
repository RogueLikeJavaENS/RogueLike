# Tutorial of RogueLike

## Summary
1. [How to play](#How-to-play)
2. [Player caracteristic](#player-caracteristic)
3. [Controls](#controls)
   - [Normal](#normal-inputs)
   - [Fighting](#fighting-inputs)
4. [Fight](#fight)
   - [Spells](#spells)
5. [Merchant](#merchant)
6. [Map](#map)
7. [Inventory](#Inventory)
8. [Room Types](#room-types)
   - [Fighting Room](#fighting-room)
   - [Merchant Room](#merchant-room)
   - [Treasure Room](#treasure-room)
   - [End Room](#end-room)
9. [Game Elements](#game-elements)
   - [Monsters](#monsters)
   - [BTC](#btc)
   - [Potions](#potions)
   - [Items](#items)
   - [Traps](#traps)
   - [Other Elements](#other-elements)
   
## How to begin 
<img src="https://user-images.githubusercontent.com/71182082/118393813-4adc1980-b641-11eb-9cad-cdff995ccc1e.png" width="450"><br>
At first you need to write your name. Keep focusing on the Panel "KEEP FOCUS HERE TO PLAY AND WRITE", to write it and press **Enter**.<br><br>
<img src="https://user-images.githubusercontent.com/71182082/118393822-5cbdbc80-b641-11eb-9da7-b8d92a974a0d.png" width="450"><br>
Here you can chose what you want to do. Switch between the choices with the keys **Z, S** and press **E** or **Enter** to select it.<br>
<br>
<img src="https://user-images.githubusercontent.com/71182082/118393835-6fd08c80-b641-11eb-9e8e-b3045652ecef.png" width="450"><br>
Now here is the capital choice, you have to choose your class. Are you gonna be a Mage, a Warrior or a Ranger ?<br>
Switch between the choice with the keys **Z, S** and press **E** or **Enter** to select it.<br>
<br>
And now the game can really begin ... <br>
<img src="https://user-images.githubusercontent.com/71182082/118393985-3e0bf580-b642-11eb-9227-574590e81f80.png" width="450"><br>
<br>

## Controls
### Normal inputs
`Movements`  **Z** Up, **Q** Left, **S** Down, **D** Right.<br>
`Interaction`  **E** Interact with entity in game, merchant or chest alike.<br>
`Menu`  **I** Open and close the playerInventory, **M** Open and close the Map, **H** Hide/show controls.<br>
`Potion`  **V** Use Healing Potion, **B** Use Elixir, **N** Use XP Bottle<br>
`Quit`  **ESC** Excape the game.<br>

### Fighting inputs
`When a monster play` You can press **ANY** key to pass their turns.<br>
`Pass your turn` When its your turn you may press W, to let the monster act.<br>
`Movement` **Caps Lock.** Lock the player, you can turn without moving<br>
`Spells` **A** Use the selected Spell, **← →** Select a Spell.<br>

## Player Caracteristic
![stats](https://user-images.githubusercontent.com/71182082/118400833-a5866d00-b663-11eb-9d3b-35241ce77b08.png)<br>
Your statistic are composed by :
   -  `XP` : number of point of experience
   -  `HP` : number of point of health
   -  `MP` : number of point of mana
   -  `LVL` : your current level
   -  `ATK` : the more attack you have, the more damage you give
   -  `DEF` : the more defense you have, the less damage you recevied
   -  `AGI` : if you have enough agility you can sometimes dodges the monster's attack
   -  `RGE` : distance of your range with a classic attack

## Fight
<img src="https://user-images.githubusercontent.com/71182082/118394027-8f1be980-b642-11eb-9049-c9796a48d474.png" width="350"><br>
A **fight** start whenever the player is in a room with a monster. The fight is organized with a turn order.<br>
When a monster is playing, you can skip the description by pressing any key.<br>
During the player turn, he has 1 action.<br>
`move`, `attack` and `use potion` are consuming the player turn.<br> 
You can open the minimap, turn yourself with **Caps. lock**, select sorts, and open your inventory without consuming your action.<br>
When a monster is killed, the hero gain **XP**, and a grave spawn at his location, the [grave](#grave) will hold [btc](#btc), [equipement](#equipement) and [items](#items) (including [potion](#potions)) and can picked up by interacting with it using **E**


### Range 
![range](https://user-images.githubusercontent.com/71182082/118395178-356aed80-b649-11eb-93ad-417a1bda05e8.png)<br>
The rectangle in light grey is your range. If a monster is in your range you can inflict damage to him.<br> 

### Spells
You can consumes an amout of mana in order to use a spell . It deals damage to all monsters within the range.<br>
Each spell has diffents range area.<br>Q
You can gain spell by gaining level.<br>
![mana-spell](https://user-images.githubusercontent.com/71182082/118395227-76fb9880-b649-11eb-8592-b0d200e01ebd.png)<br>
The spell which is selected is highlight in white and you can see just upward its cost in Mana Point.<br>

#### Ranger Spells
   - `Put Trap` : lvl 1, you can set a trap to trick the monster, be careful you can hurt yourself
   - `Basic Attack` : lvl 1, you shoot a single arrow which can go far or hit a monster  
   - `Fire Arrow` : lvl 2, you shoot an arrow surrounded by flame to hit the first monster in front of you 
   - `Dash` : lvl 3, with your great speed you move fast to the last position of your range
   - `Heal` : lvl 4, with some medicinal herb you heal yourself
   - `Sniper` : lvl 5, with this shoot of precision you hit all the monster you encounter
   - `100 arrows` : lvl 6, has a master ranger you sumon 100 arrows and destroy everything in front of you
   
#### Mage Spells
   - `Fire Aura` : lvl 1, cast a fire wall around you 
   - `Basic Attack` : lvl 1, hit with your magic staff
   - `Fire Ball` : lvl 2, throw a ball of fire on the first monster you found 
   - `Teleport` : lvl 3, teleport you at the end of your range
   - `Heal` : lvl 4, can heal yourself
   - `Fire Stroke` : lvl 5, throw a powerful strike of fire which hit all the monster he encounters
   - `HellWave` : lvl 6, throw a powerful wave of fire in front of you
   

#### Warrior Spells
   - `Basic Attack` : lvl 1, hit the monster in front of you with your sword 
   - `Strong Punch` : lvl 1, powerful hit to the monster in front of you 
   - `TourbiLOL` : lvl 2, you turn and turn and slice all the monster around you
   - `Iron Skin` : lvl 3, you make yourself more resistant 
   - `Charge` : lvl 4, you charge and hit every monster on your path
   - `Throw Axe` : lvl 5, with a perfect throw of axe you hit the first monster in your range
   - `Ground Punch` : lvl 6, a powerful punch on the ground hit all the enemies around you

## Merchant
![merchant](https://user-images.githubusercontent.com/57185748/115144943-96ee6b00-a04f-11eb-8c06-6c8837ba9554.png)<br>
You can encounter Potion Merchant in the dungeon and interact with him with **E**<br>
<img src="https://user-images.githubusercontent.com/71182082/118340168-3dc50a80-b51b-11eb-9b4c-c3d544f80bff.png" width="500"><br>
You can choose to buy him or sell him potions, equipments. Of course, the merchant will sell you items at a higher price than he gonna buy yours.<br>
Or to attack him, but be ready to face the consequences.<br>
<img src="https://user-images.githubusercontent.com/71182082/118340212-59c8ac00-b51b-11eb-837d-890d12b8f461.png" width="500"><br>
You can find all the item and stuff the merchant sell here.<br>
Use **Q, D** to switch between the items and the equipment.<br>
Use **Z, S** to switch between the stuff you can buy.<br>
On the top you can see how much gold you have, how much the selected item cost and it's effect on the player.<br>
On the bottom you can see what equipment you wear.<br>
The same interface is used to sell some stuff to the merchant.<br>

## Map
<img src="https://user-images.githubusercontent.com/71182082/118340551-5255d280-b51c-11eb-9a06-dd1a29e9a018.png" width="500"><br>
By opening the map with the key **M**, you can see the rooms of the current floor of the dungeon.<br>
You can't see a room on your map if you don't have visited her already.<br>
The ways to see all the map are to visit all the room or use a Map of the Floor.<br>
<img src="https://user-images.githubusercontent.com/71182082/118396389-a6150880-b64f-11eb-924f-c7b705204cdb.png" width="500"><br>
When you are in the dungeon you can see where you are with a mini-map in the right of the screen.<br> 

## Inventory
By opening the invenotry with the key **I** you can see all the items and the equipments you have and all your stats.<br>
You can also use it (for the item) or equipy it (for the equipment) by pressing **E**.<br>
To move on your inventory, use **Q, D** to switch between items and equipment and use **Z, S** to choose the items or equipment.<br>
<img src="https://user-images.githubusercontent.com/71182082/118340767-05263080-b51d-11eb-8560-50eabd203bc7.png" width="800"><br>
On the item part you can find all your potions, keys and also the Map of the Floor.<br>

<img src="https://user-images.githubusercontent.com/71182082/118340779-10795c00-b51d-11eb-8372-28a779756d78.png" width="800"><br>
On the equipment part you can find all your equipment. Those in red are the one you used right know you can unequip them by pressing **E**.<br>

## Room Types

### Start Room
<img src="https://user-images.githubusercontent.com/71182082/118396217-c8f2ed00-b64e-11eb-8bc5-88226f719972.png" width="350"><br>
This room is the room where you spwan in a new floor of the dungeon. You can find a chest in order to be equiped for the adventure.<br>

### Fighting Room
<img src="https://user-images.githubusercontent.com/71182082/118396235-e1fb9e00-b64e-11eb-9dfa-d3dd1f14d1aa.png" width="350"><br>
In the fighting room, the player may encounters **Monsters**.<br>
The fight start and continue whenever there is **one** monster in the room.<br>
  
### Merchant Room
<img src="https://user-images.githubusercontent.com/71182082/118396351-7534d380-b64f-11eb-81b7-662be47827ce.png" width="350"><br>
The player can find **Merchant** Room, try to interact with him by pressing **E** !<br>
  
### Treasure Roomaa
<img src="https://user-images.githubusercontent.com/71182082/118396276-1a9b7780-b64f-11eb-94c3-57d047c76e95.png" width="350"><br>
In treasure room, you can find **BTC** and Potions.<br>

### Boss Room
// photo de la room
In this room, you can find a boss. You have to beat him in order to gain a Floor Key, which permit to go to the stair. <br>
![boss-door](https://user-images.githubusercontent.com/71182082/118397081-a4990f80-b652-11eb-9944-b268f8c4c4fb.png)<br>
This room is initialy close. If you want to open the door you have to turn on all the [button](#button) of this floor.<br>

### End Room
<img src="https://user-images.githubusercontent.com/57185748/115143026-293d4180-a045-11eb-855d-1360b5a174b1.png" width="350"><br>
**Caution !** When You take a stair, you go up in the dungeon, but you _can't turn back..._
The monsters up there are stronger, you may want to level up before going upstairs.

# Game Elements
## Monsters
![aggro](https://user-images.githubusercontent.com/71182082/118394001-6398ff00-b642-11eb-90b3-cd169d03a6fd.png)<br>
When you encounter monsters, some of them may want to attack you. When they decide to attack you their feet became red.<br>

### Goblin
![Goblin](https://user-images.githubusercontent.com/71182082/115144434-e2534a00-a04c-11eb-9bcf-55cf8ad6ab97.png)<br>
The Goblin is quick but once you decrease his life enough he prefers to ran away.<br>

### Skeleton
![Skeleton](https://user-images.githubusercontent.com/71182082/115144394-a91ada00-a04c-11eb-9b46-5febe13361bc.png)<br>
The Skeleton is an angry monster once he sees you, he will follow you to the death.<br>

### Wizard 
![wizard](https://user-images.githubusercontent.com/71182082/118396976-3fddb500-b652-11eb-8fc9-2f803bbff666.png)<br>
The Wizard can cast spell and attack you from a long distance. His spells are not very strong but if you don't pay attention they can decrease a lot your life.<br>

### Orc
![orc](https://user-images.githubusercontent.com/71182082/118400190-d87b3180-b660-11eb-8e6f-1301f277e6be.png)<br>
The Orc is powerful and enraged. The damages he inflict are sizable.<br>

###  Bat
![bat](https://user-images.githubusercontent.com/71182082/118397184-11140e80-b653-11eb-974d-f857ce7735b0.png)<br>
A bat is just a bat which live on this tower. The bat is not strong, it's just a bat.<br>

### Mimic 
The mimic looks exactly like a chest. But once you try to open it, it reveal it's true face and attack you.<br>

### Merchant Monster
You choose to attack the merchant, what a bad idea. Now he's angry and want to kill you, don't underestimate him, his strength is incredible.<br>

### Vampire
![vampire](https://user-images.githubusercontent.com/71182082/118400218-ffd1fe80-b660-11eb-8117-5f26127ded1b.png)<br>
A vampire never dies, he would rather became a bat. But infortunatly for him, a bat can die.<br>

### Zombie
![zombie](https://user-images.githubusercontent.com/71182082/118397280-8253c180-b653-11eb-9543-2987bab613ac.png)<br>
The undead are back, but they can die by your hand.<br>

## Boss

### Killer Rabbit
This big pink rabbit has a key but don't want to give it to you. If you want to continue your ascent you have to beat him.<br>
Be careful he throws explosive carrots.

## BTC
![BTC](https://user-images.githubusercontent.com/71182082/115144673-33b00900-a04e-11eb-8c5c-1cc132de6027.png)<br>
You can find BTC on the floor in a lot of room, or drop them on monster you kill.<br>
Thoses BTC allow you to buy some potion to the merchant.<br>

## Equipment 
The equipment are here to improve you stats. They can increase different type of stats like the ATK or the DEF.<br>
A weapon will increase your ATK, and an armor will increase your DEF and sometimes other stats.<br>
You can find equipment in chest, grave or buy them at the merchant. To equip them go on your inventory.<br>

### Type of Equipment
The type of equipment are :
   - `Weapon` : you need a weapon to beat your enemies
   - `Shield` : used to counter the attack of monsters
   - `Armor` : protect your body it is important
   - `Boot` : wear some shoes you're not a hobbit
   - `Glove` : to protect your delicate hands
   - `Helmet` : protect your head is necessary
   - `Pant` : wear a pant and explore the world


### Rarity of Equipment
They are different level of rarity for the equipment. The higher a rarity is the more the equipment is efficient.<br>
This efficiency is associate with a level for an equipment, an armor level 1 with a high rarity is as efficient as an armor level 5 with a low rarity.<br>
The rarity are :
   - `L` (Legendary)
   - `S` (Mythic)
   - `A` (Heroic)
   - `B` (Epic)
   - `C` (Rare)
   - `D` (Uncommon)
   - `E` (Common)

## Potions

You can find potions on the treasure room or buy them at the merchant room.<br>
Potion have multiple uses and could be very useful during a fight.<br>
![NbPotion](https://user-images.githubusercontent.com/71182082/115144954-a077d300-a04f-11eb-8378-f5878bb695c8.png)<br>
Be careful : Your HP and MP can't be greater than the maximum (in this example you can't have more than 100 HP even if you use a health potion).<br>
However your potion will not be consummed if you try to use it while being either at maxHp or maxMp.<br>

### Health Potion
![Health Potion](https://user-images.githubusercontent.com/71182082/115144735-a02b0800-a04e-11eb-89cd-5d3cb5eb59fa.png)<br>
Use her to gain health.<br>

### Elixir
![Elixir](https://user-images.githubusercontent.com/71182082/115144727-8f7a9200-a04e-11eb-9a0b-cb0d7166fb95.png)<br>
Use her to gain Mana.<br>

### XP Bottle
![image](https://user-images.githubusercontent.com/71182082/115144747-b042e780-a04e-11eb-9d84-bd391c360dd3.png)<br>
Use her to gain Xp.<br>

## Items

### Floor Key
This key is given by the boss when you defeat him. It's permit you to open the door of the End Room and go to next floor.<br> 

### Golden Key
You can find this key in a Chest or you can buy one of the at the merchant.<br>
It's permit to open a [Golden Chest](#Chest-and-Golden-Chest).<br>

### Map of the Floor
You can find this map on a Grave or a Chest but they are rare, or you can buy one at the merchant.<br>

## Traps
The traps are here to complicate your movment in a room.<br>

### Spike
![spike](https://user-images.githubusercontent.com/71182082/118399882-91d90780-b65f-11eb-86ae-c5188fe6e0fe.png)<br>
You can walk on spike, but you're gonna be hurt.<br>

### Hole
![hole](https://user-images.githubusercontent.com/71182082/118399898-a4534100-b65f-11eb-8b15-0d8b81abd409.png)<br>
When you walk in a hole you are teleport in the start room of the floor. The fall hurt you of course.<br>

## Other elements

### Chest and Golden Chest
![chest](https://user-images.githubusercontent.com/71182082/118399910-b92fd480-b65f-11eb-874f-338afa4d0884.png) Chest <br>
![golden-chest](https://user-images.githubusercontent.com/71182082/118399957-f5633500-b65f-11eb-929b-b28692fc5f34.png) Golden Chest<br>
In many room you can find a Chest, and in a treasure room you can find a Golden Chest.<br>
The chest contains equipments and items, and the Golden Chest is full of more rare equipment.<br>

### Button
![red-button](https://user-images.githubusercontent.com/71182082/118396944-16bd2480-b652-11eb-9a2a-cf5ff17129dc.png)<br>
![green-button](https://user-images.githubusercontent.com/71182082/118396955-2ccae500-b652-11eb-9951-3cf2f6d2e367.png)<br>
To open the door of the [Boss Room](#boss-room) you have to activate all button (turn them in green) by pressing **E**.<br>
This button are dispersed in the room of the floor.<br>

### Grave
![grave](https://user-images.githubusercontent.com/48914003/118402766-e75ee580-b65a-11eb-96ba-e84b321b1d8f.png)<br>
A grave will be spawned each time a monster is killed, you can pick up all your reward by coming close to it and pressing **E**.


