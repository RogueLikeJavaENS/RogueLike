# Tutorial of RogueLike

## Summary
1. [Controls](#controls)
   - [Normal](#normal-inputs)
   - [Fighting](#fighting-inputs)
2. [Fight](#fight)
   - [Spells](#spells)
4. [Merchant](#merchant)
5. [Map](#map)
6. [Inventory](#Inventory)
7. [Room Types](#room-types)
   - [Fighting Room](#fighting-room)
   - [Merchant Room](#merchant-room)
   - [Treasure Room](#treasure-room)
   - [End Room](#end-room)
8. [Game Elements](#game-elements)
   - [Monsters](#monsters)
   - [BTC](#btc)
   - [Potions](#potions)
   


## Controls
### Normal inputs
`Movements`  **Z** Up, **Q** Left, **S** Down, **D** Right.<br>
`Interaction`  **E** Interact with an game.entity (Merchants).<br>
`Menu`  **I** Open and close the playerInventory, **M** Open and close the Map, **H** Hide/show controls.<br>
`Potion`  **V** Use Healing Potion, **B** Use Elixir, **N** Use XP Bottle<br>
`Quit`  **ESC** Excape the game.<br>

### Fighting inputs
`When a monster play` You can press **ANY** key to pass their turns.<br>
`Pass your turn` When its your turn you may press W, to let the monster act.<br>
`Movement` **Caps Lock.** Lock the player, you can turn without moving<br>
`Spells` **A** Use the selected Spell, **← →** Select a Spell.<br>

## Fight
<img src="https://user-images.githubusercontent.com/57185748/115145041-05cbc400-a050-11eb-99d7-4e3600ae8415.png" width="350"><br>
A **fight** start whenever the player is in a room with a monster. The fight is organized with a turn order.<br>
When a monster is playing, you can skip the description by pressing any key.<br>
During the player turn, he has 1 action.<br>
`move`, `attack` and `use potion` are consuming the player turn.<br> 
You can open the minimap, turn yourself with **Caps. lock**, select sorts, and open your inventory without consuming your action.<br>
When a monster is killed, the hero gain **XP**, [BTC](#btc) and [Potions](#potions).


### Spells
You can consumes an amout of mana in order to use a spell . It deals damage to all monsters within the range.<br>
Each spell has diffents range area.<br>
You can gain spell by gaining level.<br>

#### Ranger Spells
   - `Put Trap` : lvl 1, you can set a trap to trick the monster, be careful you can hurt yourself
   - `Basic Attack` : lvl 1, don't consume mana and have a long range 
   - `Fire Arrow` : lvl 2, du more damage than a basick attack, hut only the first monster in the range
   - `Dash` : lvl 3, you can advance to the end of the range
   - `Heal` : lvl 4, can heal yourself
   - `Sniper` : lvl 5, hurt all the monster in the range
   - `100 arrows` : lvl 6, have a special range and make a lot of damage
   
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
![menuMerchant](https://user-images.githubusercontent.com/71182082/118045032-b0958080-b377-11eb-9592-948f35a71793.png)<br>
You can choose to buy him or sell him potions, equipments. Of course <br>
Or to attack him, but be ready to face the consequences.<br>

## Map
<img src="https://user-images.githubusercontent.com/57185748/115144962-aa013b00-a04f-11eb-8e2e-3c7c51660b85.png" width="350"><br>
By openning the map, you can see all rooms of the current floor of the dungeon.

## Inventory
The playerInventory isn't used in this version... please follow us to see the progress.

## Room Types
### Fighting Room
<img src="https://user-images.githubusercontent.com/57185748/115142625-1d508000-a043-11eb-95cd-cd7f7f5b3c67.png" width="350"><br>
In the fighting room, the player may encounters **Monsters**.
The fight start and continue whenever there is **one** monster in the room.
  
### Merchant Room
<img src="https://user-images.githubusercontent.com/57185748/115142959-c3e95080-a044-11eb-8a45-e42e66ac058a.png" width="350"><br>
The player can find **Merchant** Room, try to interact with him by pressing **E** !
  
### Treasure Room
<img src="https://user-images.githubusercontent.com/57185748/115143030-2b9f9b80-a045-11eb-9864-5c005c88eb13.png" width="350"><br>
In treasure room, you can find **BTC** and Potions

### End Room
<img src="https://user-images.githubusercontent.com/57185748/115143026-293d4180-a045-11eb-855d-1360b5a174b1.png" width="350"><br>
**Caution !** When You take a stair, you go up in the dungeon, but you _can't turn back..._
The monsters up there are stronger, you may want to level up before going upstairs.

# Game Elements
## Monsters
![Agro](https://user-images.githubusercontent.com/71182082/115145205-af12ba00-a050-11eb-94b6-2769c4c871a3.png)<br>
When you encounter monsters, some of them may want to attack you. When they decide to attack you their color changes to orange (like the bottom monster on the bottom-left of the room).<br>

### Goblin
![Goblin](https://user-images.githubusercontent.com/71182082/115144434-e2534a00-a04c-11eb-9bcf-55cf8ad6ab97.png)<br>
The Goblin is quick but once you decrease his life enough he prefers to ran away.<br>

### Skeleton
![Skeleton](https://user-images.githubusercontent.com/71182082/115144394-a91ada00-a04c-11eb-9b46-5febe13361bc.png)<br>
The Skeleton is an angry monster once he sees you, he will follow you to the death.<br>

## BTC
![BTC](https://user-images.githubusercontent.com/71182082/115144673-33b00900-a04e-11eb-8c5c-1cc132de6027.png)<br>
You can find BTC on the floor in a lot of room, or drop them on monster you kill.<br>
Thoses BTC allow you to buy some potion to the merchant.<br>


## Potions
![Potion](https://user-images.githubusercontent.com/71182082/115144721-825da300-a04e-11eb-9691-d53f02732e79.png)<br>
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


=======

