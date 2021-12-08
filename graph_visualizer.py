import networkx as nx
import matplotlib.pyplot as plt

with open('./graph.txt') as f:
    lines = f.read().splitlines()
    g = nx.Graph()

    for line in lines:
        splitted_line = list(map(lambda x: x.strip(), line.split(' ')))
        data = dict()

        for arg in splitted_line:
            arg = arg.split('=')
            data[arg[0]] = arg[1]

        if 'flot=' in line:
            parent_label = data['ID'].split(':')[0]
            child_label = data['ID'].split(':')[1]

            edge_color = 'black'
            show_edge = True

            if parent_label == 'source':
                edge_color = 'blue'
                #show_edge = False

            if child_label == 'puits':
                edge_color = 'red'
                #show_edge = False

            if show_edge:
                g.add_edge(parent_label, child_label, length=100,
                           capacity=int(data['capacite']), color=edge_color, label=f'{data["flot"]}/{data["capacite"]}')

        else:
            if data['ID'].split('-')[0] != 'source' and data['ID'].split('-')[0] != 'puits':
                x = int(data['ID'].split('-')[0]) * 10 * 10
                y = int(data['ID'].split('-')[1]) * -10 * 10

            elif data['ID'].split('-')[0] == 'source':
                x = -200
                y = -150

            elif data['ID'].split('-')[0] == 'puits':
                x = 150
                y = 200

            label = f'{data["ID"]}'  # - a={data["a"]} b={data["b"]}
            g.add_node(label, pos=(x, y))

    plt.figure(figsize=(24, 24))

    ax = plt.gca()
    ax.set_title('Titre')

    edges = g.edges()
    colors = [g[u][v]['color'] for u, v in edges]
    weights = [2 for u, v in edges]

    positions = nx.get_node_attributes(g, 'pos')
    nx.draw(g, positions, edge_color=colors,
            width=weights, with_labels=True, ax=ax)

    labels = nx.get_edge_attributes(g, 'label')

    nx.draw_networkx_edge_labels(
        g, positions, edge_labels=labels, label_pos=0.5, ax=ax)

    _ = ax.axis('off')
    plt.savefig(f'./graph.png')
    plt.show()
