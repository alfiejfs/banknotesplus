# banknotesplus
A fully configurable yet simple bank notes plugin for Spigot 1.8

![Image of banknote](https://i.imgur.com/kS0bSAy.png)


![Transaction messages](https://i.imgur.com/ZhQpjnR.png)

# Configuration

```
bank-notes:
  name: "&d&lBank Note &e&l(%amount%)"
  lore:
    - "&aThis bank note is worth:"
    - "&7 - &e%amount%"
    - "&aIt was withdrawn by:"
    - "&7 - &e%player%"
    - "&aRight click to redeem!"
messages:
  no-permission: "&c&l(!) &cYou don't have permission to do that!"
  player-only: "&c&l(!) &cYou need to be a player to do that!"
  not-enough-money: "&c&l(!) &cYou don't have enough money to do that!"
  dropped-note: "&c&l(!) &cYour inventory was full so the bank note (worth &a$%amount%&c) was dropped on the ground."
  added-note: "&c&l(!) &cAdded a bank note worth &a$%amount%&c was added to your inventory!"
  redeemed-note: "&c&l(!) &cYou have redeemed a note for &a$%value%&c."
  gave-note: "&c&l(!) &cYou have gave a note worth &a$%value%&c to %player%."
  received-note: "&c&l(!) &cYou have received a note worth &a$%value%&c from %player%."
  ```

