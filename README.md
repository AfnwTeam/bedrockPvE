# bedrockPvE
Bedrock PvE in AFNW

## Commands
- `bpe`
  - tp to `bedrockpve` world
- `bpespawn` (op only)
  - mobs spawn in `bedrockpve` world
- `bpereload` (op only)
  - reload `config.yml`

## config.yml
```yml
- ${profileName1} # e.g. Default
  ${x1},${y1},${z1}
    - type: ${EntityType1}
      count: ${countOfMOBs1}
    - type: ${EntityType2}
      count: ${countOfMOBs2}
  ${x2},${y2},${z2}
    - type: ${EntityType3}
      count: ${countOfMOBs3}
- ${profileName2} # e.g. Second
  ${x3},${y3},${z3}
    - type: ${EntityType4}
      count: ${countOfMOBs4}
```
