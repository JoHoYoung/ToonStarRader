class Data {
  constructor(opt) {
    this.graphSetting = opt.graphSetting;
    this.refinedData = [];
    this.scores = [];
    this.axis = [];
    this.column = [];
    this.seriesId = "toon117"
  }

  init = async () => {
    await this.setRawData();
    this.parseRawData();
    this.parseScore();
    this.setColumn();
    this.setAxis();
  }

  setColumn = () => {
    this.column = ["avg"];
    for(let i=0;i<this.graphSetting.termNumber;i++){
      this.column.push(parseFloat(this.refinedData[i].avg.toFixed(2)));
    }
  }

  setSeriesId = (val) => {
    this.seriesId = val;
  }

  setMinDate = (val) => {
    this.minDate = val;
  }

  setMaxDate = (val) => {
    this.maxDate = val;
  }

  getSeriesId = () => {
    return this.seriesId;
  }

  getMinDate = () => {
    return this.minDate;
  }

  getMaxDate = () => {
    return this.maxDate;
  }

  getColumn = () => {
    return this.column;
  }

  setAxis = () => {
    this.axis = [];
    let pivotTime = this.graphSetting.stringToDate(this.graphSetting.startDate);
    for(let i=0;i<this.graphSetting.termNumber;i++){
      let d = pivotTime.add("seconds", this.graphSetting.term);
      this.axis.push(d.format("YY-MM-DD-HH:mm:ss"))
    }
  }

  getAxis = () => {
    return this.axis;
  }

  requestRawData = async () => {
    return new Promise((resolve, reject) => {
      $.ajax({
        type: "GET",
        url: `http://localhost:8080/star/read?startDate=${this.graphSetting.startDate}&endDate=${this.graphSetting.endDate}&seriesId=${this.seriesId}`,
        dataType: "text",
        error: function () {
          reject();
        },
        success: function (Parse_data) {
          resolve(JSON.parse(Parse_data).data);
        }
      });
    })
  };

  setRawData = async () => {
    this.rawData = await this.requestRawData();
    this.rawData = this.rawData.map(el => JSON.parse(el));
    this.setMinDate(this.graphSetting.startDate);
    this.setMaxDate(this.graphSetting.endDate);
  }

  parseRawData = () => {
    const Data = () => {
      return {
        stars: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      }
    }

    this.refinedData = [];
    for (let i = 0; i < this.graphSetting.termNumber; i++) {
      this.refinedData.push(Data());
    }

    const getDiffFromStart = (time) => {
      time = moment(time, "YYYY-MM-DDTHH:mm:ss");
      return time.diff(this.graphSetting.stringToDate(this.graphSetting.startDate)) / 1000
    }

    //Big(O(n))
    for (let i = 0; i < this.rawData.length; i++) {
      let index = parseInt(getDiffFromStart(this.rawData[i].createdAt) / this.graphSetting.term)
      if (this.refinedData[index]) {
        this.refinedData[index].stars[parseInt(this.rawData[i].score * 2)]++;
      }
    }

    // Big(O(20n))
    this.refinedData = this.refinedData.map(el => {
      let sum = 0;
      let count = 0;
      for (let i = 0; i < el.stars.length; i++) {
        sum += (parseFloat((i + 1) / 2)) * el.stars[i];
        count += el.stars[i];
      }

      el.avg = count === 0 ? 0 : parseFloat(sum / count);
      return el;
    })
  }

  parseScore = () => {
    this.scores = [];
    const score = () => {
      return [["0.0"], ["0.5"], ["1.0"], ["1.5"], ["2.0"], ["2.5"], ["3.0"],
        ["3.5"], ["4.0"], ["4.5"], ["5.0"], ["5.5"], ["6.0"], ["6.5"], ["7.5"], ["8.0"], ["8.5"], ["9.0"], ["9.5"], ["10.0"]]
    }
    //Big(O(20n))
    for (let i = 0; i < this.refinedData.length; i++) {
      let s = score();
      for (let j = 0; j < this.refinedData[i].stars.length; j++) {
        s[j].push(this.refinedData[i].stars[j])
      }
      this.scores.push(s);
    }
  }

  getScore = (idx) => {
    return this.scores[idx];
  }

}

module.exports = Data;
