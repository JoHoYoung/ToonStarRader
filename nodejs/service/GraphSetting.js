class GraphSetting {
  constructor() {
    // default setting
    this.startDate = '2019-11-10-22:00:00';
    this.endDate = '2019-11-11-22:00:00';
    this.termNumber = 10;
    this.setInterval();
    this.setTerm();
  }

  stringToDate = (d) => {
    return moment(d, "YYYY-MM-DD-HH:mm:ss")
  }

  setInterval = () => {
    this.interval = this.stringToDate(this.endDate).diff(this.stringToDate(this.startDate)) / 1000;
  }

  setTermNumber = (val) => {
    this.termNumber = val;
    this.setTerm();
  }

  setTerm = () => {
    this.term = this.interval / this.termNumber;
  }
}

module.exports = new GraphSetting();
