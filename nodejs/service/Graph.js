class Graph {
  constructor() {
    this.json = {data:{},title: {}};
  }

  setProperty = (val) => {
    this.json = val;
  }

  setTitle = (text) => {
    this.json.title.text = text;
  }

  setAttribute = (key, attr) => {
    this.json[key] = attr;
  }

  setType = (type) => {
    this.json.data.type = type;
  }

  setColor = (prop, data) => {
    if (!this.json.data.colors) {
      this.json.data.colors = {};
    }

    this.json.data.colors[prop] = data;
  }

  setColumn = (data) => {
    this.json.data.columns = data
  }

  setOnclick = (fn) => {
    this.json.data.onclick = fn;
  }

  setAxis = (data) => {
    this.json.axis = {x: {type: "category"}};
    this.json.axis.x.categories = data;
  }

  setBindTo = (id) => {
    this.json.bindto = id;
  }

  genGraph = () => {
   this.graph = bb.generate(this.json)
  }

  loadColumn = (data) => {
    this.graph.load({columns: data})
  }

}

module.exports = Graph;