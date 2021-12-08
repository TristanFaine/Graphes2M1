import matplotlib.pyplot as plt
from PIL import Image

with open('graph.txt') as f:
    lines = f.read().splitlines()

    im = Image.new('RGB', (255, 255), color='white')
    pixels = im.load()

    for line in lines:
        print(line)
        plan = 1 if line.split(':')[0] == 'Plan1' else 2
        node = line.split(':')[1]
        x = int(node.split('-')[0])
        y = int(node.split('-')[1])

        pixels[x, y] = (255, 0, 0) if plan == 1 else (0, 0, 255)

    im.save("pixel_grid.png")
