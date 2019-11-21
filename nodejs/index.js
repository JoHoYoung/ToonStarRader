import GraphSetting from './service/GraphSetting.js';
import Graph from './service/Graph.js'
import Data from './service/Data.js'

const data = new Data({graphSetting: GraphSetting})
let PieChart;
let LineChart;

//유저의 인풋값에서 Date가져옴
function getDateString(id){
  let values = document.getElementById(id).children;
  let Day = values[0].value;
  let Time = values[1].value;
  return `${Day}-${Time}:00`
}



// 리셋 버튼이 눌렸을때.
async function onSubmit(){
  let SDate = getDateString("SDATE");
  let EDate = getDateString("EDATE");
  let termNumber = document.getElementById("termNumber").value;
  let seriesId = document.getElementById("seriesId").value;


  // 데이터를 새로 가져와야 할때만 요청
  // 기존 데이터의 범위를 벗어나면 데이터 요청
  if(data.getSeriesId() !== seriesId || SDate < data.getMinDate() || EDate > data.getMaxDate()){
    GraphSetting.startDate = SDate;
    GraphSetting.endDate = EDate;
    GraphSetting.setInterval();
    GraphSetting.setTermNumber(termNumber);

    data.setSeriesId(seriesId);
    data.init().then(() => {
      renderGraph();
    });

  }else{
    // rawData는 변하지 않고 그래프 데이터만 수정.
    GraphSetting.startDate = SDate;
    GraphSetting.endDate = EDate;
    GraphSetting.setInterval();
    GraphSetting.setTermNumber(termNumber);

    data.parseRawData();
    data.parseScore();
    data.setColumn();
    data.setAxis();

    renderGraph()
  }
}


// 그래프 그리기.
function renderGraph(){
  PieChart = new Graph();
  PieChart.setType("pie");
  PieChart.setAttribute("pie", {
    label: {
      format: function (v) {
        return d3.format("")(v);
      }
    }
  });
  PieChart.setColumn(data.getScore(0));
  PieChart.setBindTo("#starPie");
  PieChart.setTitle(`Star's ratio`)
  PieChart.genGraph();


  LineChart = new Graph();
  LineChart.setColor("avg", "#00912F");
  LineChart.setOnclick((element) => {
    PieChart.loadColumn(data.getScore(element.index - 1))
  })
  LineChart.setBindTo("#timeseriesChart")
  LineChart.setAxis(data.getAxis());
  LineChart.setColumn([data.getColumn()]);
  LineChart.setTitle(`Average ${GraphSetting.startDate} ~ ${GraphSetting.endDate}`)
  LineChart.genGraph();
  document.getElementById("done").onclick = onSubmit;
}


//처음 Dom load시 데이터 처리 후, 그래프 그린다.
data.init().then(() => {
  console.log("RENDER")
  renderGraph();
});
