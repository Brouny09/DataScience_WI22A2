'''import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

df = pd.read_csv("/Users/jeremianeuhaus/Desktop/Studium/Semester 2/DataScience/Project/SmallDataSet.csv", encoding="utf-8")

print(df.AnzahlTodesfall)

sns.relplot(
    data=df,
    x="Meldedatum", y="AnzahlFall"
    
)
plt.show()'''


import seaborn as sns
import matplotlib.pyplot as plt

# Beispiel-Daten
x_vals = [1, 2, 3, 4, 5]
y_vals = [5, 9, 3, 7, 2]

# Erstellen des Balkendiagramms mit Seaborn
sns.set_style("whitegrid")
ax = sns.barplot(x=x_vals, y=y_vals)

# Titel und Beschriftungen hinzufügen
ax.set_title("Mein Balkendiagramm")
ax.set_xlabel("X-Achse")
ax.set_ylabel("Y-Achse")

# Anzeige des Diagramms
plt.show()

