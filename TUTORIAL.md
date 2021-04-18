# Tutorial of RogueLike

## Summary
1. [Controls](#controls)
   - [Normal](#normal-inputs)
   - [Fighting](#fighting-inputs)
2. [Fight](#fight)
3. [Merchant](#merchant)
4. [Map](#map)
5. [Inventory](#inventory)
6. [Room Types](#room-types)
   - [Fighting Room](#fighting-room)
   - [Merchant Room](#merchant-room)
   - [Treasure Room](#treasure-room)
   - [End Room](#end-room)
7. [Game Elements](#game-elements)
   - [Monsters](#monsters)
   - [BTC](#btc)
   - [Potions](#potions)
   - [Spells](#spells)


## Controls
### Normal inputs
`Movements`  **Z** Up, **Q** Left, **S** Down, **D** Right.<br>
`Interaction`  **E** Interact with an entity (Merchants).<br>
`Menu`  **I** Open and close the inventory, **M** Open and close the Map, **H** Hide/show controls.<br>
`Potion`  **V** Use Healing Potion, **B** Use Elixir, **N** Use XP Bottle<br>
`Quit`  **ESC** Excape the game.<br>

### Fighting inputs
`When a monster play` You can press **ANY** key to pass their turns.<br>
`Pass your turn` When its your turn you may press W, to let the monster act.<br>
`Movement` **Caps Lock.** Lock the player, you can turn without moving<br>
`Spells` **A** Use the selected Spell, **1 2 3 4 5 6 7 8 9** Select a spell.<br>

## Fight
<img src="https://user-images.githubusercontent.com/57185748/115145041-05cbc400-a050-11eb-99d7-4e3600ae8415.png" width="350"><br>
A **fight** start whenever the player is in a room with a monster. The fight is organized with a turn order.<br>
When a monster is playing, you can skip the description by pressing any key.<br>
During the player turn, he has 1 action.<br>
`move`, `attack` and `use potion` are consuming the player turn.<br> 
You can open the minimap, turn yourself with **Caps. lock**, select sorts, and open the inventory without consuming your action.<br>
When a monster is killed, the hero gain **XP**, [BTC](#btc) and [Potions](#potions).

## Merchant
![merchant](https://user-images.githubusercontent.com/57185748/115144943-96ee6b00-a04f-11eb-8c06-6c8837ba9554.png)<br>
You can encounter Potion Merchant in the dungeon. You can buy for **10** [BTC](#btc) a random potion.

## Map
<img src="https://user-images.githubusercontent.com/57185748/115144962-aa013b00-a04f-11eb-8e2e-3c7c51660b85.png" width="350"><br>
By openning the map, you can see all rooms of the current floor of the dungeon.

## Inventory
The inventory isn't used in this version... please follow us to see the progress.

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

## Spells
A spell consumes an amout of mana. It deals damage to all monsters within the range.<br>
Each spells has diffents range area.<br>
### Basic Attack
![image](https://user-images.githubusercontent.com/57185748/115145221-c81b6b00-a050-11eb-8fed-479363e065e1.png)<br>

### Fire Aura 
![image](https://user-images.githubusercontent.com/57185748/115145227-cea9e280-a050-11eb-9fe8-a4b029f2810f.png)<br>

### Fire Ball
![image](https://user-images.githubusercontent.com/57185748/115145233-d36e9680-a050-11eb-9f10-590bd982c6bc.png)<br>
